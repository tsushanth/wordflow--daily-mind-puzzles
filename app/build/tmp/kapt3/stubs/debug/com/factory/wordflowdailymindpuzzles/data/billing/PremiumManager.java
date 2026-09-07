package com.factory.wordflowdailymindpuzzles.data.billing;

/**
 * Tracks and persists the user's premium/entitlement state locally.
 *
 * The source of truth for *whether a purchase is currently valid* is always Play Billing
 * ([BillingManager] queries it on every app start/resume); this class only caches the last
 * known result so the UI has something to render instantly and offline.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tJ\u0006\u0010\u0018\u001a\u00020\u000bJ\u0016\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000bJ\b\u0010\u001d\u001a\u00020\u0007H\u0002J\u0006\u0010\u001e\u001a\u00020\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_entitlement", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumEntitlement;", "_hintCredits", "", "_isPremium", "", "entitlement", "Lkotlinx/coroutines/flow/StateFlow;", "getEntitlement", "()Lkotlinx/coroutines/flow/StateFlow;", "hintCredits", "getHintCredits", "isPremium", "prefs", "Landroid/content/SharedPreferences;", "addHintCredits", "", "amount", "consumeHintCredit", "grantPremium", "productId", "", "isLifetime", "loadEntitlement", "revokePremium", "Companion", "app_debug"})
public final class PremiumManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.factory.wordflowdailymindpuzzles.data.billing.PremiumEntitlement> _entitlement = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.data.billing.PremiumEntitlement> entitlement = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isPremium = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPremium = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _hintCredits = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> hintCredits = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "premium_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IS_PREMIUM = "is_premium";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PRODUCT_ID = "product_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IS_LIFETIME = "is_lifetime";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_HINT_CREDITS = "hint_credits";
    @org.jetbrains.annotations.NotNull()
    public static final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager.Companion Companion = null;
    
    public PremiumManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.data.billing.PremiumEntitlement> getEntitlement() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPremium() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getHintCredits() {
        return null;
    }
    
    public final void grantPremium(@org.jetbrains.annotations.NotNull()
    java.lang.String productId, boolean isLifetime) {
    }
    
    public final void revokePremium() {
    }
    
    public final void addHintCredits(int amount) {
    }
    
    /**
     * Returns true and decrements by one if a credit was available.
     */
    public final boolean consumeHintCredit() {
        return false;
    }
    
    private final com.factory.wordflowdailymindpuzzles.data.billing.PremiumEntitlement loadEntitlement() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager$Companion;", "", "()V", "KEY_HINT_CREDITS", "", "KEY_IS_LIFETIME", "KEY_IS_PREMIUM", "KEY_PRODUCT_ID", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}