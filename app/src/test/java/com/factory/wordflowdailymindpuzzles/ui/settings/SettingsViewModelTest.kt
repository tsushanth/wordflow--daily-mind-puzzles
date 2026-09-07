package com.factory.wordflowdailymindpuzzles.ui.settings

import app.cash.turbine.test
import com.factory.wordflowdailymindpuzzles.data.billing.BillingManager
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumEntitlement
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val billingManager: BillingManager = mockk(relaxed = true)
    private val premiumManager: PremiumManager = mockk(relaxed = true)

    private val entitlementFlow = MutableStateFlow(PremiumEntitlement())
    private val hintCreditsFlow = MutableStateFlow(0)
    private val isPremiumFlow = MutableStateFlow(false)

    private fun createViewModel(): SettingsViewModel {
        every { premiumManager.entitlement } returns entitlementFlow
        every { premiumManager.hintCredits } returns hintCreditsFlow
        every { premiumManager.isPremium } returns isPremiumFlow
        return SettingsViewModel(billingManager, premiumManager)
    }

    @Test
    fun `initial state reflects default entitlement and hint credits`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.uiState.test {
            assertEquals(SettingsUiState(), awaitItem())
        }
        assertNull(viewModel.statusMessage.value)
    }

    @Test
    fun `state reflects premium entitlement and hint credits`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        val entitlement = PremiumEntitlement(isPremium = true, productId = "sub.yearly", isLifetime = false)

        viewModel.uiState.test {
            assertEquals(SettingsUiState(), awaitItem())
            entitlementFlow.value = entitlement
            hintCreditsFlow.value = 3
            assertEquals(SettingsUiState(entitlement = entitlement, hintCredits = 3), awaitItem())
        }
    }

    @Test
    fun `onRestoreClicked restores purchases and reports success when premium`() =
        runTest(mainDispatcherRule.testDispatcher) {
            coEvery { billingManager.restorePurchases() } answers { isPremiumFlow.value = true }
            val viewModel = createViewModel()

            viewModel.onRestoreClicked()
            advanceUntilIdle()

            coVerify(exactly = 1) { billingManager.restorePurchases() }
            assertEquals("Purchases restored.", viewModel.statusMessage.value)
        }

    @Test
    fun `onRestoreClicked reports no purchases when still not premium`() =
        runTest(mainDispatcherRule.testDispatcher) {
            coEvery { billingManager.restorePurchases() } returns Unit
            val viewModel = createViewModel()

            viewModel.onRestoreClicked()
            advanceUntilIdle()

            assertEquals("No previous purchases found.", viewModel.statusMessage.value)
        }

    @Test
    fun `onLinkOpenFailed sets an error message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.onLinkOpenFailed()

        assertEquals("Couldn't open the link. Please check your browser app.", viewModel.statusMessage.value)
    }

    @Test
    fun `dismissStatusMessage clears the message`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()
        viewModel.onLinkOpenFailed()

        viewModel.dismissStatusMessage()

        assertNull(viewModel.statusMessage.value)
    }
}
