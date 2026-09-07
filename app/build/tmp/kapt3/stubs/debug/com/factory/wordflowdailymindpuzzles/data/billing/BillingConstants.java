package com.factory.wordflowdailymindpuzzles.data.billing;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u000e\u0010\u0011\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingConstants;", "", "()V", "HINT_PACK_CREDITS", "", "IAP_HINT_PACK", "", "IAP_LIFETIME", "INAPP_PRODUCT_IDS", "", "getINAPP_PRODUCT_IDS", "()Ljava/util/List;", "PACKAGE", "PREMIUM_ENTITLEMENT_PRODUCT_IDS", "getPREMIUM_ENTITLEMENT_PRODUCT_IDS", "SUBSCRIPTION_PRODUCT_IDS", "getSUBSCRIPTION_PRODUCT_IDS", "SUB_MONTHLY", "SUB_WEEKLY", "SUB_YEARLY", "app_debug"})
public final class BillingConstants {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PACKAGE = "com.factory.wordflowdailymindpuzzles";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SUB_WEEKLY = "com.factory.wordflowdailymindpuzzles.subscription.weekly";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SUB_MONTHLY = "com.factory.wordflowdailymindpuzzles.subscription.monthly";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SUB_YEARLY = "com.factory.wordflowdailymindpuzzles.subscription.yearly";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String IAP_LIFETIME = "com.factory.wordflowdailymindpuzzles.subscription.lifetime";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String IAP_HINT_PACK = "com.factory.wordflowdailymindpuzzles.small_iap";
    
    /**
     * Recurring products, queried and sold as [ProductType.SUBS].
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> SUBSCRIPTION_PRODUCT_IDS = null;
    
    /**
     * One-time products, queried and sold as [ProductType.INAPP].
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> INAPP_PRODUCT_IDS = null;
    
    /**
     * Any of these being actively owned grants full premium access.
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> PREMIUM_ENTITLEMENT_PRODUCT_IDS = null;
    
    /**
     * Purchasing this consumable tops up hint credits instead of granting premium.
     */
    public static final int HINT_PACK_CREDITS = 5;
    @org.jetbrains.annotations.NotNull()
    public static final com.factory.wordflowdailymindpuzzles.data.billing.BillingConstants INSTANCE = null;
    
    private BillingConstants() {
        super();
    }
    
    /**
     * Recurring products, queried and sold as [ProductType.SUBS].
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSUBSCRIPTION_PRODUCT_IDS() {
        return null;
    }
    
    /**
     * One-time products, queried and sold as [ProductType.INAPP].
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getINAPP_PRODUCT_IDS() {
        return null;
    }
    
    /**
     * Any of these being actively owned grants full premium access.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPREMIUM_ENTITLEMENT_PRODUCT_IDS() {
        return null;
    }
}