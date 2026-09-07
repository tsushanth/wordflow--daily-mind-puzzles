package com.factory.wordflowdailymindpuzzles.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factory.wordflowdailymindpuzzles.data.billing.BillingManager
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumEntitlement
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val entitlement: PremiumEntitlement = PremiumEntitlement(),
    val hintCredits: Int = 0
)

class SettingsViewModel(
    private val billingManager: BillingManager,
    private val premiumManager: PremiumManager
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        premiumManager.entitlement,
        premiumManager.hintCredits
    ) { entitlement, hintCredits ->
        SettingsUiState(entitlement = entitlement, hintCredits = hintCredits)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun onRestoreClicked() {
        viewModelScope.launch {
            billingManager.restorePurchases()
            _statusMessage.value = if (premiumManager.isPremium.value) {
                "Purchases restored."
            } else {
                "No previous purchases found."
            }
        }
    }

    fun onLinkOpenFailed() {
        _statusMessage.value = "Couldn't open the link. Please check your browser app."
    }

    fun dismissStatusMessage() {
        _statusMessage.value = null
    }
}
