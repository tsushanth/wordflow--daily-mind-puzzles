package com.factory.wordflowdailymindpuzzles.ui.premium

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factory.wordflowdailymindpuzzles.data.billing.BillingConnectionState
import com.factory.wordflowdailymindpuzzles.data.billing.BillingManager
import com.factory.wordflowdailymindpuzzles.data.billing.HintPackOffer
import com.factory.wordflowdailymindpuzzles.data.billing.PaywallTier
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.data.billing.PurchaseUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PaywallUiState(
    val isConnecting: Boolean = true,
    val isOffline: Boolean = false,
    val isPremium: Boolean = false,
    val hintCredits: Int = 0,
    val prices: Map<String, String> = emptyMap(),
    val statusMessage: String? = null
)

class PaywallViewModel(
    private val billingManager: BillingManager,
    private val premiumManager: PremiumManager
) : ViewModel() {

    private val _statusMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<PaywallUiState> = combine(
        billingManager.connectionState,
        billingManager.productDetails,
        premiumManager.isPremium,
        premiumManager.hintCredits,
        _statusMessage
    ) { connection, products, isPremium, hintCredits, statusMessage ->
        PaywallUiState(
            isConnecting = connection is BillingConnectionState.Connecting,
            isOffline = connection is BillingConnectionState.Disconnected,
            isPremium = isPremium,
            hintCredits = hintCredits,
            prices = products.mapValues { (_, details) ->
                details.subscriptionOfferDetails?.firstOrNull()
                    ?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice
                    ?: details.oneTimePurchaseOfferDetails?.formattedPrice
                    ?: ""
            },
            statusMessage = statusMessage
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaywallUiState())

    init {
        viewModelScope.launch {
            billingManager.purchaseEvents.collect { event ->
                _statusMessage.value = when (event) {
                    is PurchaseUiEvent.Success -> "Welcome to Premium!"
                    is PurchaseUiEvent.Cancelled -> null
                    is PurchaseUiEvent.Pending -> "Your purchase is pending approval. Premium unlocks once it's confirmed."
                    is PurchaseUiEvent.Error -> event.message
                }
            }
        }
    }

    fun priceFor(tier: PaywallTier): String = uiState.value.prices[tier.productId] ?: tier.defaultPriceText

    fun hintPackPrice(): String = uiState.value.prices[HintPackOffer.productId] ?: HintPackOffer.defaultPriceText

    fun onTierSelected(tier: PaywallTier, activity: Activity) {
        viewModelScope.launch { billingManager.purchase(activity, tier.productId) }
    }

    fun onHintPackSelected(activity: Activity) {
        viewModelScope.launch { billingManager.purchase(activity, HintPackOffer.productId) }
    }

    fun onRestoreClicked() {
        viewModelScope.launch {
            billingManager.restorePurchases()
            if (_statusMessage.value == null) {
                _statusMessage.value = if (premiumManager.isPremium.value) "Purchases restored." else "No previous purchases found."
            }
        }
    }

    fun dismissStatusMessage() {
        _statusMessage.value = null
    }

    fun onLinkOpenFailed() {
        _statusMessage.value = "Couldn't open the link. Please check your browser app."
    }
}
