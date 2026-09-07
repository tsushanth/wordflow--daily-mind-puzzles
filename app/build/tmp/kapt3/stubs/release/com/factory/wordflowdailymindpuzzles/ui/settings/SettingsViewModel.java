package com.factory.wordflowdailymindpuzzles.ui.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u0012R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u0015"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/ui/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "billingManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;", "premiumManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "(Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;)V", "_statusMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "statusMessage", "Lkotlinx/coroutines/flow/StateFlow;", "getStatusMessage", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "Lcom/factory/wordflowdailymindpuzzles/ui/settings/SettingsUiState;", "getUiState", "dismissStatusMessage", "", "onLinkOpenFailed", "onRestoreClicked", "app_release"})
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.BillingManager billingManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.settings.SettingsUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> statusMessage = null;
    
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.BillingManager billingManager, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.settings.SettingsUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getStatusMessage() {
        return null;
    }
    
    public final void onRestoreClicked() {
    }
    
    public final void onLinkOpenFailed() {
    }
    
    public final void dismissStatusMessage() {
    }
}