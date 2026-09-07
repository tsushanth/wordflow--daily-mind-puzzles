package com.factory.wordflowdailymindpuzzles.data.billing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PremiumManagerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        // Each test gets a clean slate; prefs otherwise persist across tests in the same process.
        context.getSharedPreferences("premium_prefs", Context.MODE_PRIVATE).edit().clear().commit()
    }

    @Test
    fun `initial state is not premium with no credits`() {
        val manager = PremiumManager(context)

        assertFalse(manager.isPremium.value)
        assertEquals(PremiumEntitlement(), manager.entitlement.value)
        assertEquals(0, manager.hintCredits.value)
    }

    @Test
    fun `grantPremium updates isPremium and entitlement state`() {
        val manager = PremiumManager(context)

        manager.grantPremium(BillingConstants.SUB_YEARLY, isLifetime = false)

        assertTrue(manager.isPremium.value)
        assertEquals(
            PremiumEntitlement(isPremium = true, productId = BillingConstants.SUB_YEARLY, isLifetime = false),
            manager.entitlement.value
        )
    }

    @Test
    fun `grantPremium for lifetime product sets isLifetime`() {
        val manager = PremiumManager(context)

        manager.grantPremium(BillingConstants.IAP_LIFETIME, isLifetime = true)

        assertTrue(manager.entitlement.value.isLifetime)
    }

    @Test
    fun `premium state persists across manager instances`() {
        val first = PremiumManager(context)
        first.grantPremium(BillingConstants.SUB_YEARLY, isLifetime = false)

        val second = PremiumManager(context)

        assertTrue(second.isPremium.value)
        assertEquals(BillingConstants.SUB_YEARLY, second.entitlement.value.productId)
    }

    @Test
    fun `revokePremium clears state and persists`() {
        val manager = PremiumManager(context)
        manager.grantPremium(BillingConstants.SUB_YEARLY, isLifetime = false)

        manager.revokePremium()

        assertFalse(manager.isPremium.value)
        assertEquals(PremiumEntitlement(), manager.entitlement.value)

        val reloaded = PremiumManager(context)
        assertFalse(reloaded.isPremium.value)
    }

    @Test
    fun `revokePremium when not premium is a no-op`() {
        val manager = PremiumManager(context)

        manager.revokePremium()

        assertFalse(manager.isPremium.value)
        assertEquals(PremiumEntitlement(), manager.entitlement.value)
    }

    @Test
    fun `addHintCredits increments and persists credits`() {
        val manager = PremiumManager(context)

        manager.addHintCredits(5)
        manager.addHintCredits(3)

        assertEquals(8, manager.hintCredits.value)

        val reloaded = PremiumManager(context)
        assertEquals(8, reloaded.hintCredits.value)
    }

    @Test
    fun `consumeHintCredit decrements when available`() {
        val manager = PremiumManager(context)
        manager.addHintCredits(2)

        val consumed = manager.consumeHintCredit()

        assertTrue(consumed)
        assertEquals(1, manager.hintCredits.value)
    }

    @Test
    fun `consumeHintCredit returns false when no credits available`() {
        val manager = PremiumManager(context)

        val consumed = manager.consumeHintCredit()

        assertFalse(consumed)
        assertEquals(0, manager.hintCredits.value)
    }

    @Test
    fun `consumeHintCredit exhausts credits one at a time`() {
        val manager = PremiumManager(context)
        manager.addHintCredits(1)

        assertTrue(manager.consumeHintCredit())
        assertFalse(manager.consumeHintCredit())
    }
}
