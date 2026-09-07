package com.factory.wordflowdailymindpuzzles;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0018"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/WordFlowApplication;", "Landroid/app/Application;", "()V", "billingManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;", "getBillingManager", "()Lcom/factory/wordflowdailymindpuzzles/data/billing/BillingManager;", "billingManager$delegate", "Lkotlin/Lazy;", "database", "Lcom/factory/wordflowdailymindpuzzles/data/AppDatabase;", "getDatabase", "()Lcom/factory/wordflowdailymindpuzzles/data/AppDatabase;", "database$delegate", "premiumManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "getPremiumManager", "()Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "premiumManager$delegate", "repository", "Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;", "getRepository", "()Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;", "repository$delegate", "app_debug"})
public final class WordFlowApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy repository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy premiumManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy billingManager$delegate = null;
    
    public WordFlowApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.factory.wordflowdailymindpuzzles.data.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.factory.wordflowdailymindpuzzles.data.GameRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager getPremiumManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.factory.wordflowdailymindpuzzles.data.billing.BillingManager getBillingManager() {
        return null;
    }
}