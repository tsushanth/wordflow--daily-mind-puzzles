package com.factory.wordflowdailymindpuzzles.ui.premium

import android.app.Activity
import app.cash.turbine.test
import com.android.billingclient.api.ProductDetails
import com.factory.wordflowdailymindpuzzles.data.billing.BillingConnectionState
import com.factory.wordflowdailymindpuzzles.data.billing.BillingConstants
import com.factory.wordflowdailymindpuzzles.data.billing.BillingManager
import com.factory.wordflowdailymindpuzzles.data.billing.HintPackOffer
import com.factory.wordflowdailymindpuzzles.data.billing.PaywallTier
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.data.billing.PurchaseUiEvent
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class PaywallViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val billingManager: BillingManager = mockk(relaxed = true)
    private val premiumManager: PremiumManager = mockk(relaxed = true)

    private val connectionState = MutableStateFlow<BillingConnectionState>(BillingConnectionState.Connecting)
    private val productDetails = MutableStateFlow<Map<String, ProductDetails>>(emptyMap())
    private val isPremiumFlow = MutableStateFlow(false)
    private val hintCreditsFlow = MutableStateFlow(0)
    private val purchaseEvents = MutableSharedFlow<PurchaseUiEvent>(extraBufferCapacity = 1)
    private val activity: Activity = mockk(relaxed = true)

    private fun fakeProductDetails(productId: String, price: String): ProductDetails = mockk(relaxed = true) {
        every { this@mockk.productId } returns productId
        every { this@mockk.subscriptionOfferDetails } returns null
        every { this@mockk.oneTimePurchaseOfferDetails } returns mockk {
            every { formattedPrice } returns price
        }
    }

    private fun createViewModel(): PaywallViewModel {
        every { billingManager.connectionState } returns connectionState
        every { billingManager.productDetails } returns productDetails
        every { billingManager.purchaseEvents } returns purchaseEvents
        every { premiumManager.isPremium } returns isPremiumFlow
        every { premiumManager.hintCredits } returns hintCreditsFlow
        return PaywallViewModel(billingManager, premiumManager)
    }

    @Test
    fun `initial state reflects connecting with no products`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(true, state.isConnecting)
            assertEquals(false, state.isOffline)
            assertEquals(false, state.isPremium)
            assertEquals(0, state.hintCredits)
            assertEquals(emptyMap<String, String>(), state.prices)
        }
    }

    @Test
    fun `state reflects connected products, premium and hint credits`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.uiState.test {
            awaitItem()
            connectionState.value = BillingConnectionState.Connected
            productDetails.value = mapOf(BillingConstants.SUB_YEARLY to fakeProductDetails(BillingConstants.SUB_YEARLY, "$35.99"))
            isPremiumFlow.value = true
            hintCreditsFlow.value = 2

            val state = expectMostRecentItem()
            assertEquals(false, state.isConnecting)
            assertEquals(true, state.isPremium)
            assertEquals(2, state.hintCredits)
            assertEquals("$35.99", state.prices[BillingConstants.SUB_YEARLY])
        }
    }

    @Test
    fun `disconnected connection state marks the paywall offline`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.uiState.test {
            awaitItem()
            connectionState.value = BillingConnectionState.Disconnected("no network")
            assertEquals(true, awaitItem().isOffline)
        }
    }

    @Test
    fun `purchase success event sets a welcome status message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        purchaseEvents.emit(PurchaseUiEvent.Success(BillingConstants.SUB_YEARLY))
        advanceUntilIdle()

        assertEquals("Welcome to Premium!", viewModel.uiState.value.statusMessage)
    }

    @Test
    fun `purchase pending event sets a pending status message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        purchaseEvents.emit(PurchaseUiEvent.Pending)
        advanceUntilIdle()

        assertEquals(
            "Your purchase is pending approval. Premium unlocks once it's confirmed.",
            viewModel.uiState.value.statusMessage
        )
    }

    @Test
    fun `purchase error event surfaces its message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        purchaseEvents.emit(PurchaseUiEvent.Error("card declined"))
        advanceUntilIdle()

        assertEquals("card declined", viewModel.uiState.value.statusMessage)
    }

    @Test
    fun `purchase cancelled event clears the status message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()
        purchaseEvents.emit(PurchaseUiEvent.Error("card declined"))
        advanceUntilIdle()

        purchaseEvents.emit(PurchaseUiEvent.Cancelled)
        advanceUntilIdle()

        assertNull(viewModel.uiState.value.statusMessage)
    }

    @Test
    fun `onTierSelected launches a purchase for the tier's product id`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onTierSelected(PaywallTier.YEARLY, activity)
        advanceUntilIdle()

        coVerify(exactly = 1) { billingManager.purchase(activity, BillingConstants.SUB_YEARLY) }
    }

    @Test
    fun `onHintPackSelected launches a purchase for the hint pack`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onHintPackSelected(activity)
        advanceUntilIdle()

        coVerify(exactly = 1) { billingManager.purchase(activity, HintPackOffer.productId) }
    }

    @Test
    fun `onRestoreClicked restores and reports success when premium`() = runTest(mainDispatcherRule.testDispatcher) {
        coEvery { billingManager.restorePurchases() } answers { isPremiumFlow.value = true }
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onRestoreClicked()
        advanceUntilIdle()

        coVerify(exactly = 1) { billingManager.restorePurchases() }
        assertEquals("Purchases restored.", viewModel.uiState.value.statusMessage)
    }

    @Test
    fun `onRestoreClicked reports no purchases found when still not premium`() =
        runTest(mainDispatcherRule.testDispatcher) {
            coEvery { billingManager.restorePurchases() } returns Unit
            val viewModel = createViewModel()
            advanceUntilIdle()

            viewModel.onRestoreClicked()
            advanceUntilIdle()

            assertEquals("No previous purchases found.", viewModel.uiState.value.statusMessage)
        }

    @Test
    fun `dismissStatusMessage clears the message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()
        purchaseEvents.emit(PurchaseUiEvent.Error("boom"))
        advanceUntilIdle()

        viewModel.dismissStatusMessage()

        assertNull(viewModel.uiState.value.statusMessage)
    }

    @Test
    fun `onLinkOpenFailed sets a browser error message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onLinkOpenFailed()

        assertEquals("Couldn't open the link. Please check your browser app.", viewModel.uiState.value.statusMessage)
    }

    @Test
    fun `priceFor falls back to the tier's default price when not loaded`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(PaywallTier.YEARLY.defaultPriceText, viewModel.priceFor(PaywallTier.YEARLY))
    }

    @Test
    fun `hintPackPrice falls back to the default price when not loaded`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        advanceUntilIdle()

        assertEquals(HintPackOffer.defaultPriceText, viewModel.hintPackPrice())
    }
}
