package com.factory.wordflowdailymindpuzzles.data.billing

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Owns the [BillingClient] connection and exposes purchase/product state as flows.
 *
 * Lives for the process lifetime (created once from [android.app.Application]); the
 * [Activity] is only ever passed in for the duration of a single [launchBillingFlow] call.
 */
class BillingManager(
    private val context: Context,
    private val premiumManager: PremiumManager,
    billingClientFactory: (PurchasesUpdatedListener) -> BillingClient = { listener ->
        BillingClient.newBuilder(context)
            .setListener(listener)
            .enablePendingPurchases()
            .build()
    }
) : PurchasesUpdatedListener {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val billingClient: BillingClient = billingClientFactory(this)

    private val _connectionState = MutableStateFlow<BillingConnectionState>(BillingConnectionState.Connecting)
    val connectionState: StateFlow<BillingConnectionState> = _connectionState.asStateFlow()

    private val _productDetails = MutableStateFlow<Map<String, ProductDetails>>(emptyMap())
    val productDetails: StateFlow<Map<String, ProductDetails>> = _productDetails.asStateFlow()

    private val _purchaseEvents = MutableSharedFlow<PurchaseUiEvent>(extraBufferCapacity = 1)
    val purchaseEvents: SharedFlow<PurchaseUiEvent> = _purchaseEvents.asSharedFlow()

    private var retryDelayMs = INITIAL_RETRY_DELAY_MS

    init {
        scope.launch {
            connectWithRetry()
            queryAllProductDetails()
            refreshPurchases()
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        scope.launch {
            when (billingResult.responseCode) {
                BillingResponseCode.OK -> handlePurchases(purchases.orEmpty())
                BillingResponseCode.USER_CANCELED -> _purchaseEvents.emit(PurchaseUiEvent.Cancelled)
                BillingResponseCode.ITEM_ALREADY_OWNED -> {
                    refreshPurchases()
                    _purchaseEvents.emit(PurchaseUiEvent.Error("You already own this item. Restoring it now."))
                }
                else -> _purchaseEvents.emit(
                    PurchaseUiEvent.Error(billingResult.debugMessage.ifBlank { "Purchase failed. Please try again." })
                )
            }
        }
    }

    /** Looks up the queried [ProductDetails] and launches the Play purchase sheet for it. */
    suspend fun purchase(activity: Activity, productId: String) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            _purchaseEvents.emit(PurchaseUiEvent.Error("No internet connection. Please check your network and try again."))
            return
        }
        if (!ensureConnected()) {
            _purchaseEvents.emit(PurchaseUiEvent.Error("Unable to reach Google Play. Please try again shortly."))
            return
        }
        val details = _productDetails.value[productId]
        if (details == null) {
            _purchaseEvents.emit(PurchaseUiEvent.Error("This product isn't available right now."))
            return
        }

        val offerToken = details.subscriptionOfferDetails?.firstOrNull()?.offerToken
        val productParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(details)
            .apply { offerToken?.let { setOfferToken(it) } }
            .build()

        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productParams))
            .build()

        val result = billingClient.launchBillingFlow(activity, flowParams)
        if (result.responseCode != BillingResponseCode.OK) {
            _purchaseEvents.emit(
                PurchaseUiEvent.Error(result.debugMessage.ifBlank { "Couldn't start the purchase. Please try again." })
            )
        }
    }

    /** Re-syncs owned purchases with Play Billing; used on startup, resume, and "Restore Purchases". */
    suspend fun refreshPurchases() {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            _purchaseEvents.emit(PurchaseUiEvent.Error("No internet connection. Please check your network and try again."))
            return
        }
        if (!ensureConnected()) return

        val subs = billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()
        ).purchasesList
        val inapp = billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
        ).purchasesList

        val all = subs + inapp
        val purchased = all.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }
        if (purchased.isNotEmpty()) {
            handlePurchases(purchased)
        }

        val hasActiveEntitlement = purchased.any { purchase ->
            purchase.products.any { it in BillingConstants.PREMIUM_ENTITLEMENT_PRODUCT_IDS }
        }
        if (!hasActiveEntitlement) {
            premiumManager.revokePremium()
        }

        if (all.any { it.purchaseState == Purchase.PurchaseState.PENDING }) {
            _purchaseEvents.emit(PurchaseUiEvent.Pending)
        }
    }

    suspend fun restorePurchases() {
        refreshPurchases()
    }

    private suspend fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            if (purchase.purchaseState != Purchase.PurchaseState.PURCHASED) continue
            if (!PurchaseVerifier.isValid(purchase)) {
                Log.w(TAG, "Skipping purchase with invalid signature: ${purchase.orderId}")
                continue
            }

            val productId = purchase.products.firstOrNull() ?: continue
            grantEntitlement(productId)

            if (purchase.isAcknowledged) continue

            if (productId == BillingConstants.IAP_HINT_PACK) {
                billingClient.consumePurchase(
                    ConsumeParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
                )
            } else {
                billingClient.acknowledgePurchase(
                    AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
                )
            }

            _purchaseEvents.emit(PurchaseUiEvent.Success(productId))
        }
    }

    private fun grantEntitlement(productId: String) {
        when {
            productId == BillingConstants.IAP_HINT_PACK ->
                premiumManager.addHintCredits(BillingConstants.HINT_PACK_CREDITS)
            productId in BillingConstants.PREMIUM_ENTITLEMENT_PRODUCT_IDS ->
                premiumManager.grantPremium(productId, isLifetime = productId == BillingConstants.IAP_LIFETIME)
        }
    }

    private suspend fun queryAllProductDetails() {
        val subsProducts = BillingConstants.SUBSCRIPTION_PRODUCT_IDS.map {
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        }
        val inappProducts = BillingConstants.INAPP_PRODUCT_IDS.map {
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }

        val subsResult = billingClient.queryProductDetails(
            QueryProductDetailsParams.newBuilder().setProductList(subsProducts).build()
        )
        val inappResult = billingClient.queryProductDetails(
            QueryProductDetailsParams.newBuilder().setProductList(inappProducts).build()
        )

        val combined = (subsResult.productDetailsList.orEmpty() + inappResult.productDetailsList.orEmpty())
            .associateBy { it.productId }
        _productDetails.value = combined
    }

    private suspend fun ensureConnected(): Boolean {
        if (billingClient.isReady) return true
        return connectWithRetry(maxAttempts = 1)
    }

    private suspend fun connectWithRetry(maxAttempts: Int = MAX_RETRY_ATTEMPTS): Boolean {
        repeat(maxAttempts) { attempt ->
            _connectionState.value = BillingConnectionState.Connecting
            val connected = connectOnce()
            if (connected) {
                retryDelayMs = INITIAL_RETRY_DELAY_MS
                _connectionState.value = BillingConnectionState.Connected
                return true
            }
            if (attempt < maxAttempts - 1) {
                delay(retryDelayMs)
                retryDelayMs = (retryDelayMs * 2).coerceAtMost(MAX_RETRY_DELAY_MS)
            }
        }
        _connectionState.value = BillingConnectionState.Disconnected("Couldn't connect to Google Play.")
        return false
    }

    private suspend fun connectOnce(): Boolean = suspendCancellableCoroutine { continuation ->
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (continuation.isActive) {
                    continuation.resume(billingResult.responseCode == BillingResponseCode.OK)
                }
            }

            override fun onBillingServiceDisconnected() {
                _connectionState.value = BillingConnectionState.Disconnected("Disconnected from Google Play.")
                if (continuation.isActive) {
                    continuation.resume(false)
                }
            }
        })
    }

    companion object {
        private const val TAG = "BillingManager"
        private const val INITIAL_RETRY_DELAY_MS = 1000L
        private const val MAX_RETRY_DELAY_MS = 15000L
        private const val MAX_RETRY_ATTEMPTS = 3
    }
}
