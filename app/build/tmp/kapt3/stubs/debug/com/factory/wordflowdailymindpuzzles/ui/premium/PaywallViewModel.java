package com.factory.wordflowdailymindpuzzles.ui.premium;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\tJ\u000e\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0010J\u0006\u0010\u0016\u001a\u00020\u0010J\u0016\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0019R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001b"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/ui/premium/PaywallViewModel;", "Landroidx/lifecycle/ViewModel;", "billingManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;", "premiumManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "(Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;)V", "_statusMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/factory/wordflowdailymindpuzzles/ui/premium/PaywallUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "dismissStatusMessage", "", "hintPackPrice", "onHintPackSelected", "activity", "Landroid/app/Activity;", "onLinkOpenFailed", "onRestoreClicked", "onTierSelected", "tier", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PaywallTier;", "priceFor", "app_debug"})
public final class PaywallViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.BillingManager billingManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.premium.PaywallUiState> uiState = null;
    
    public PaywallViewModel(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.BillingManager billingManager, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.premium.PaywallUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String priceFor(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PaywallTier tier) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String hintPackPrice() {
        return null;
    }
    
    public final void onTierSelected(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PaywallTier tier, @org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void onHintPackSelected(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
    }
    
    public final void onRestoreClicked() {
    }
    
    public final void dismissStatusMessage() {
    }
    
    public final void onLinkOpenFailed() {
    }
}