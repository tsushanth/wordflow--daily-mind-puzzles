package com.factory.wordflowdailymindpuzzles.data.billing

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Tracks and persists the user's premium/entitlement state locally.
 *
 * The source of truth for *whether a purchase is currently valid* is always Play Billing
 * ([BillingManager] queries it on every app start/resume); this class only caches the last
 * known result so the UI has something to render instantly and offline.
 */
class PremiumManager(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _entitlement = MutableStateFlow(loadEntitlement())
    val entitlement: StateFlow<PremiumEntitlement> = _entitlement.asStateFlow()

    private val _isPremium = MutableStateFlow(_entitlement.value.isPremium)
    val isPremium: StateFlow<Boolean> = _isPremium.asStateFlow()

    private val _hintCredits = MutableStateFlow(prefs.getInt(KEY_HINT_CREDITS, 0))
    val hintCredits: StateFlow<Int> = _hintCredits.asStateFlow()

    fun grantPremium(productId: String, isLifetime: Boolean) {
        val updated = PremiumEntitlement(isPremium = true, productId = productId, isLifetime = isLifetime)
        _entitlement.value = updated
        _isPremium.value = true
        prefs.edit {
            putBoolean(KEY_IS_PREMIUM, true)
            putString(KEY_PRODUCT_ID, productId)
            putBoolean(KEY_IS_LIFETIME, isLifetime)
        }
    }

    fun revokePremium() {
        if (!_entitlement.value.isPremium) return
        _entitlement.value = PremiumEntitlement()
        _isPremium.value = false
        prefs.edit {
            putBoolean(KEY_IS_PREMIUM, false)
            remove(KEY_PRODUCT_ID)
            putBoolean(KEY_IS_LIFETIME, false)
        }
    }

    fun addHintCredits(amount: Int) {
        val updated = _hintCredits.value + amount
        _hintCredits.value = updated
        prefs.edit { putInt(KEY_HINT_CREDITS, updated) }
    }

    /** Returns true and decrements by one if a credit was available. */
    fun consumeHintCredit(): Boolean {
        val current = _hintCredits.value
        if (current <= 0) return false
        _hintCredits.value = current - 1
        prefs.edit { putInt(KEY_HINT_CREDITS, current - 1) }
        return true
    }

    private fun loadEntitlement(): PremiumEntitlement = PremiumEntitlement(
        isPremium = prefs.getBoolean(KEY_IS_PREMIUM, false),
        productId = prefs.getString(KEY_PRODUCT_ID, null),
        isLifetime = prefs.getBoolean(KEY_IS_LIFETIME, false)
    )

    companion object {
        private const val PREFS_NAME = "premium_prefs"
        private const val KEY_IS_PREMIUM = "is_premium"
        private const val KEY_PRODUCT_ID = "product_id"
        private const val KEY_IS_LIFETIME = "is_lifetime"
        private const val KEY_HINT_CREDITS = "hint_credits"
    }
}
