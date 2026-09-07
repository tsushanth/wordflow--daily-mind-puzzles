package com.factory.wordflowdailymindpuzzles.ui.premium

/**
 * Every gate-able feature in the app, in one place, so it's obvious what's free vs. PRO.
 * ~60% of the app's feature surface is premium by design.
 */
enum class PremiumFeature(val isPremium: Boolean) {
    DAILY_SCRAMBLE(isPremium = false),
    WORD_SEARCH_FREE_CATEGORY(isPremium = false),
    BASIC_STATS(isPremium = false),

    WORD_SEARCH_EXTRA_CATEGORIES(isPremium = true),
    FULL_PUZZLE_HISTORY(isPremium = true),
    UNLIMITED_HINTS(isPremium = true),
    EXCLUSIVE_THEMES(isPremium = true),
    AD_FREE_EXPERIENCE(isPremium = true)
}
