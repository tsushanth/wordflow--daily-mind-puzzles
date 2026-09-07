package com.factory.wordflowdailymindpuzzles.data.billing;

/**
 * Owns the [BillingClient] connection and exposes purchase/product state as flows.
 *
 * Lives for the process lifetime (created once from [android.app.Application]); the
 * [Activity] is only ever passed in for the duration of a single [launchBillingFlow] call.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 >2\u00020\u0001:\u0001>B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\u000e\u0010#\u001a\u00020$H\u0082@\u00a2\u0006\u0002\u0010%J\u0018\u0010&\u001a\u00020$2\b\b\u0002\u0010\'\u001a\u00020(H\u0082@\u00a2\u0006\u0002\u0010)J\u000e\u0010*\u001a\u00020$H\u0082@\u00a2\u0006\u0002\u0010%J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u000fH\u0002J\u001c\u0010.\u001a\u00020,2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020100H\u0082@\u00a2\u0006\u0002\u00102J \u00103\u001a\u00020,2\u0006\u00104\u001a\u0002052\u000e\u0010/\u001a\n\u0012\u0004\u0012\u000201\u0018\u000106H\u0016J\u001e\u00107\u001a\u00020,2\u0006\u00108\u001a\u0002092\u0006\u0010-\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010:J\u000e\u0010;\u001a\u00020,H\u0082@\u00a2\u0006\u0002\u0010%J\u000e\u0010<\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010%J\u000e\u0010=\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010%R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\u0019\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;", "Lcom/android/billingclient/api/PurchasesUpdatedListener;", "context", "Landroid/content/Context;", "premiumManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "billingClientFactory", "Lkotlin/Function1;", "Lcom/android/billingclient/api/BillingClient;", "(Landroid/content/Context;Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;Lkotlin/jvm/functions/Function1;)V", "_connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingConnectionState;", "_productDetails", "", "", "Lcom/android/billingclient/api/ProductDetails;", "_purchaseEvents", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PurchaseUiEvent;", "billingClient", "connectionState", "Lkotlinx/coroutines/flow/StateFlow;", "getConnectionState", "()Lkotlinx/coroutines/flow/StateFlow;", "productDetails", "getProductDetails", "purchaseEvents", "Lkotlinx/coroutines/flow/SharedFlow;", "getPurchaseEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "retryDelayMs", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "connectOnce", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectWithRetry", "maxAttempts", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ensureConnected", "grantEntitlement", "", "productId", "handlePurchases", "purchases", "", "Lcom/android/billingclient/api/Purchase;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onPurchasesUpdated", "billingResult", "Lcom/android/billingclient/api/BillingResult;", "", "purchase", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "queryAllProductDetails", "refreshPurchases", "restorePurchases", "Companion", "app_release"})
public final class BillingManager implements com.android.billingclient.api.PurchasesUpdatedListener {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final com.android.billingclient.api.BillingClient billingClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.factory.wordflowdailymindpuzzles.data.billing.BillingConnectionState> _connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.data.billing.BillingConnectionState> connectionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.android.billingclient.api.ProductDetails>> _productDetails = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.android.billingclient.api.ProductDetails>> productDetails = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.factory.wordflowdailymindpuzzles.data.billing.PurchaseUiEvent> _purchaseEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.factory.wordflowdailymindpuzzles.data.billing.PurchaseUiEvent> purchaseEvents = null;
    private long retryDelayMs = 1000L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "BillingManager";
    private static final long INITIAL_RETRY_DELAY_MS = 1000L;
    private static final long MAX_RETRY_DELAY_MS = 15000L;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    @org.jetbrains.annotations.NotNull()
    public static final com.factory.wordflowdailymindpuzzles.data.billing.BillingManager.Companion Companion = null;
    
    public BillingManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.android.billingclient.api.PurchasesUpdatedListener, ? extends com.android.billingclient.api.BillingClient> billingClientFactory) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.data.billing.BillingConnectionState> getConnectionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.android.billingclient.api.ProductDetails>> getProductDetails() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.factory.wordflowdailymindpuzzles.data.billing.PurchaseUiEvent> getPurchaseEvents() {
        return null;
    }
    
    @java.lang.Override()
    public void onPurchasesUpdated(@org.jetbrains.annotations.NotNull()
    com.android.billingclient.api.BillingResult billingResult, @org.jetbrains.annotations.Nullable()
    java.util.List<com.android.billingclient.api.Purchase> purchases) {
    }
    
    /**
     * Looks up the queried [ProductDetails] and launches the Play purchase sheet for it.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object purchase(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity, @org.jetbrains.annotations.NotNull()
    java.lang.String productId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Re-syncs owned purchases with Play Billing; used on startup, resume, and "Restore Purchases".
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshPurchases(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object restorePurchases(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object handlePurchases(java.util.List<? extends com.android.billingclient.api.Purchase> purchases, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void grantEntitlement(java.lang.String productId) {
    }
    
    private final java.lang.Object queryAllProductDetails(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object ensureConnected(kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object connectWithRetry(int maxAttempts, kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object connectOnce(kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager$Companion;", "", "()V", "INITIAL_RETRY_DELAY_MS", "", "MAX_RETRY_ATTEMPTS", "", "MAX_RETRY_DELAY_MS", "TAG", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}