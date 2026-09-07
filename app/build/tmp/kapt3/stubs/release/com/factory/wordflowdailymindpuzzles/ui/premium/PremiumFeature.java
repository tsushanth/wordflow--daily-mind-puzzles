package com.factory.wordflowdailymindpuzzles.ui.premium;

/**
 * Every gate-able feature in the app, in one place, so it's obvious what's free vs. PRO.
 * ~60% of the app's feature surface is premium by design.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/ui/premium/PremiumFeature;", "", "isPremium", "", "(Ljava/lang/String;IZ)V", "()Z", "DAILY_SCRAMBLE", "WORD_SEARCH_FREE_CATEGORY", "BASIC_STATS", "WORD_SEARCH_EXTRA_CATEGORIES", "FULL_PUZZLE_HISTORY", "UNLIMITED_HINTS", "EXCLUSIVE_THEMES", "AD_FREE_EXPERIENCE", "app_release"})
public enum PremiumFeature {
    /*public static final*/ DAILY_SCRAMBLE /* = new DAILY_SCRAMBLE(false) */,
    /*public static final*/ WORD_SEARCH_FREE_CATEGORY /* = new WORD_SEARCH_FREE_CATEGORY(false) */,
    /*public static final*/ BASIC_STATS /* = new BASIC_STATS(false) */,
    /*public static final*/ WORD_SEARCH_EXTRA_CATEGORIES /* = new WORD_SEARCH_EXTRA_CATEGORIES(false) */,
    /*public static final*/ FULL_PUZZLE_HISTORY /* = new FULL_PUZZLE_HISTORY(false) */,
    /*public static final*/ UNLIMITED_HINTS /* = new UNLIMITED_HINTS(false) */,
    /*public static final*/ EXCLUSIVE_THEMES /* = new EXCLUSIVE_THEMES(false) */,
    /*public static final*/ AD_FREE_EXPERIENCE /* = new AD_FREE_EXPERIENCE(false) */;
    private final boolean isPremium = false;
    
    PremiumFeature(boolean isPremium) {
    }
    
    public final boolean isPremium() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.factory.wordflowdailymindpuzzles.ui.premium.PremiumFeature> getEntries() {
        return null;
    }
}