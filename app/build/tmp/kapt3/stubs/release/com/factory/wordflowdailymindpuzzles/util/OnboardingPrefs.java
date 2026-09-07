package com.factory.wordflowdailymindpuzzles.util;

/**
 * Tracks whether the user has already been through the one-time onboarding flow.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/util/OnboardingPrefs;", "", "()V", "KEY_COMPLETED", "", "PREFS_NAME", "hasCompletedOnboarding", "", "context", "Landroid/content/Context;", "markOnboardingCompleted", "", "app_release"})
public final class OnboardingPrefs {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "onboarding_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_COMPLETED = "completed";
    @org.jetbrains.annotations.NotNull()
    public static final com.factory.wordflowdailymindpuzzles.util.OnboardingPrefs INSTANCE = null;
    
    private OnboardingPrefs() {
        super();
    }
    
    public final boolean hasCompletedOnboarding(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final void markOnboardingCompleted(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}