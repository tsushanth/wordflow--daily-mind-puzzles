package com.factory.wordflowdailymindpuzzles.data.billing

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import app.cash.turbine.test
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetailsResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResult
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * [BillingManager] is constructed with an injected [BillingClient] factory (see the
 * `billingClientFactory` constructor param) purely so a mocked [BillingClient] can be substituted
 * here; production code always uses the default factory that builds a real client.
 */
class BillingManagerTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCapabilities: NetworkCapabilities
    private lateinit var premiumManager: PremiumManager
    private lateinit var billingClient: BillingClient
    private lateinit var activity: Activity

    @Before
    fun setUp() {
        mockkStatic("com.android.billingclient.api.BillingClientKotlinKt")

        networkCapabilities = mockk(relaxed = true)
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        connectivityManager = mockk(relaxed = true)
        every { connectivityManager.activeNetwork } returns mockk<Network>(relaxed = true)
        every { connectivityManager.getNetworkCapabilities(any()) } returns networkCapabilities

        context = mockk(relaxed = true)
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager

        premiumManager = mockk(relaxed = true)
        activity = mockk(relaxed = true)

        billingClient = mockk(relaxed = true)
        every { billingClient.isReady } returns true
        every { billingClient.startConnection(any()) } answers {
            firstArg<BillingClientStateListener>().onBillingSetupFinished(billingResult(BillingClient.BillingResponseCode.OK))
        }
        every { billingClient.launchBillingFlow(any(), any()) } returns billingResult(BillingClient.BillingResponseCode.OK)
        coEvery { billingClient.queryProductDetails(any<QueryProductDetailsParams>()) } returns
            ProductDetailsResult(billingResult(BillingClient.BillingResponseCode.OK), emptyList())
        coEvery { billingClient.queryPurchasesAsync(any<QueryPurchasesParams>()) } returns
            PurchasesResult(billingResult(BillingClient.BillingResponseCode.OK), emptyList())
        coEvery { billingClient.acknowledgePurchase(any()) } returns billingResult(BillingClient.BillingResponseCode.OK)
        coEvery { billingClient.consumePurchase(any()) } returns
            ConsumeResult(billingResult(BillingClient.BillingResponseCode.OK), "token")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun billingResult(code: Int, message: String = ""): BillingResult =
        BillingResult.newBuilder().setResponseCode(code).setDebugMessage(message).build()

    private fun fakeProductDetails(productId: String): ProductDetails = mockk(relaxed = true) {
        every { this@mockk.productId } returns productId
        every { this@mockk.subscriptionOfferDetails } returns null
        every { this@mockk.oneTimePurchaseOfferDetails } returns null
    }

    private fun fakePurchase(
        productId: String,
        state: Int = Purchase.PurchaseState.PURCHASED,
        acknowledged: Boolean = false,
        token: String = "token-$productId"
    ): Purchase = mockk(relaxed = true) {
        every { this@mockk.products } returns listOf(productId)
        every { this@mockk.purchaseState } returns state
        every { this@mockk.isAcknowledged } returns acknowledged
        every { this@mockk.purchaseToken } returns token
        every { this@mockk.signature } returns "sig"
        every { this@mockk.originalJson } returns "{}"
        every { this@mockk.orderId } returns "order-$productId"
    }

    private fun createManager(): BillingManager = BillingManager(context, premiumManager) { billingClient }

    @Test
    fun `connects successfully and loads product details on startup`() = runTest(mainDispatcherRule.testDispatcher) {
        coEvery { billingClient.queryProductDetails(any<QueryProductDetailsParams>()) } returnsMany listOf(
            ProductDetailsResult(billingResult(BillingClient.BillingResponseCode.OK), listOf(fakeProductDetails(BillingConstants.SUB_YEARLY))),
            ProductDetailsResult(billingResult(BillingClient.BillingResponseCode.OK), listOf(fakeProductDetails(BillingConstants.IAP_LIFETIME)))
        )

        val manager = createManager()
        advanceUntilIdle()

        assertEquals(BillingConnectionState.Connected, manager.connectionState.value)
        assertEquals(
            setOf(BillingConstants.SUB_YEARLY, BillingConstants.IAP_LIFETIME),
            manager.productDetails.value.keys
        )
    }

    @Test
    fun `connection failure retries and ends disconnected`() = runTest(mainDispatcherRule.testDispatcher) {
        every { billingClient.startConnection(any()) } answers {
            firstArg<BillingClientStateListener>().onBillingSetupFinished(
                billingResult(BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE)
            )
        }

        val manager = createManager()
        advanceUntilIdle()

        assertTrue(manager.connectionState.value is BillingConnectionState.Disconnected)
        verify(exactly = 3) { billingClient.startConnection(any()) }
    }

    @Test
    fun `purchase launches billing flow for a known product`() = runTest(mainDispatcherRule.testDispatcher) {
        coEvery { billingClient.queryProductDetails(any<QueryProductDetailsParams>()) } returns
            ProductDetailsResult(billingResult(BillingClient.BillingResponseCode.OK), listOf(fakeProductDetails(BillingConstants.SUB_YEARLY)))

        val manager = createManager()
        advanceUntilIdle()

        manager.purchase(activity, BillingConstants.SUB_YEARLY)
        advanceUntilIdle()

        verify(exactly = 1) { billingClient.launchBillingFlow(activity, any<BillingFlowParams>()) }
    }

    @Test
    fun `purchase for unavailable product emits error`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()

        manager.purchaseEvents.test {
            manager.purchase(activity, "not.a.real.product")
            assertEquals(PurchaseUiEvent.Error("This product isn't available right now."), awaitItem())
        }
        verify(exactly = 0) { billingClient.launchBillingFlow(any(), any()) }
    }

    @Test
    fun `purchase without network emits error and never launches billing flow`() = runTest(mainDispatcherRule.testDispatcher) {
        coEvery { billingClient.queryProductDetails(any<QueryProductDetailsParams>()) } returns
            ProductDetailsResult(billingResult(BillingClient.BillingResponseCode.OK), listOf(fakeProductDetails(BillingConstants.SUB_YEARLY)))
        val manager = createManager()
        advanceUntilIdle()

        every { connectivityManager.activeNetwork } returns null

        manager.purchaseEvents.test {
            manager.purchase(activity, BillingConstants.SUB_YEARLY)
            assertEquals(
                PurchaseUiEvent.Error("No internet connection. Please check your network and try again."),
                awaitItem()
            )
        }
        verify(exactly = 0) { billingClient.launchBillingFlow(any(), any()) }
    }

    @Test
    fun `restorePurchases with no owned entitlement revokes premium`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()

        manager.restorePurchases()
        advanceUntilIdle()

        verify(atLeast = 1) { premiumManager.revokePremium() }
    }

    @Test
    fun `restorePurchases with a pending purchase emits Pending`() = runTest(mainDispatcherRule.testDispatcher) {
        val pending = fakePurchase(BillingConstants.SUB_YEARLY, state = Purchase.PurchaseState.PENDING)
        val manager = createManager()
        advanceUntilIdle()

        manager.purchaseEvents.test {
            coEvery { billingClient.queryPurchasesAsync(any<QueryPurchasesParams>()) } returnsMany listOf(
                PurchasesResult(billingResult(BillingClient.BillingResponseCode.OK), listOf(pending)),
                PurchasesResult(billingResult(BillingClient.BillingResponseCode.OK), emptyList())
            )
            manager.restorePurchases()
            assertEquals(PurchaseUiEvent.Pending, awaitItem())
        }
    }

    @Test
    fun `onPurchasesUpdated with OK grants premium, acknowledges and emits Success`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()
        val purchase = fakePurchase(BillingConstants.SUB_YEARLY)

        manager.purchaseEvents.test {
            manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.OK), mutableListOf(purchase))
            assertEquals(PurchaseUiEvent.Success(BillingConstants.SUB_YEARLY), awaitItem())
        }

        verify(exactly = 1) { premiumManager.grantPremium(BillingConstants.SUB_YEARLY, isLifetime = false) }
        coVerify(exactly = 1) { billingClient.acknowledgePurchase(any()) }
        coVerify(exactly = 0) { billingClient.consumePurchase(any()) }
    }

    @Test
    fun `onPurchasesUpdated for lifetime product grants premium as lifetime`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()
        val purchase = fakePurchase(BillingConstants.IAP_LIFETIME)

        manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.OK), mutableListOf(purchase))
        advanceUntilIdle()

        verify(exactly = 1) { premiumManager.grantPremium(BillingConstants.IAP_LIFETIME, isLifetime = true) }
    }

    @Test
    fun `onPurchasesUpdated for hint pack adds credits and consumes instead of acknowledging`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()
        val purchase = fakePurchase(BillingConstants.IAP_HINT_PACK)

        manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.OK), mutableListOf(purchase))
        advanceUntilIdle()

        verify(exactly = 1) { premiumManager.addHintCredits(BillingConstants.HINT_PACK_CREDITS) }
        coVerify(exactly = 1) { billingClient.consumePurchase(any()) }
        coVerify(exactly = 0) { billingClient.acknowledgePurchase(any()) }
        verify(exactly = 0) { premiumManager.grantPremium(any(), any()) }
    }

    @Test
    fun `onPurchasesUpdated skips already-acknowledged purchases`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()
        val purchase = fakePurchase(BillingConstants.SUB_YEARLY, acknowledged = true)

        manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.OK), mutableListOf(purchase))
        advanceUntilIdle()

        verify(exactly = 1) { premiumManager.grantPremium(BillingConstants.SUB_YEARLY, isLifetime = false) }
        coVerify(exactly = 0) { billingClient.acknowledgePurchase(any()) }
    }

    @Test
    fun `onPurchasesUpdated with USER_CANCELED emits Cancelled`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()

        manager.purchaseEvents.test {
            manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.USER_CANCELED), null)
            assertEquals(PurchaseUiEvent.Cancelled, awaitItem())
        }
    }

    @Test
    fun `onPurchasesUpdated with ITEM_ALREADY_OWNED refreshes and emits an error`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()

        manager.purchaseEvents.test {
            manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED), null)
            assertEquals(
                PurchaseUiEvent.Error("You already own this item. Restoring it now."),
                awaitItem()
            )
        }
    }

    @Test
    fun `onPurchasesUpdated with a generic failure emits its debug message`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()

        manager.purchaseEvents.test {
            manager.onPurchasesUpdated(
                billingResult(BillingClient.BillingResponseCode.ERROR, "boom"),
                null
            )
            assertEquals(PurchaseUiEvent.Error("boom"), awaitItem())
        }
    }

    @Test
    fun `onPurchasesUpdated ignores purchases that fail local signature validation`() = runTest(mainDispatcherRule.testDispatcher) {
        val manager = createManager()
        advanceUntilIdle()
        val invalid = fakePurchase(BillingConstants.SUB_YEARLY).also {
            every { it.signature } returns ""
        }

        manager.onPurchasesUpdated(billingResult(BillingClient.BillingResponseCode.OK), mutableListOf(invalid))
        advanceUntilIdle()

        verify(exactly = 0) { premiumManager.grantPremium(any(), any()) }
    }
}
