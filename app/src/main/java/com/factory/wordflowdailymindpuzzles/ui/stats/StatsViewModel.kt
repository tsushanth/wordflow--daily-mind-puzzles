package com.factory.wordflowdailymindpuzzles.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.util.StreakCalculator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/** History rows visible to non-Premium users; the rest are behind the paywall. */
const val FREE_HISTORY_LIMIT = 5

data class StatsUiState(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalScrambleSolved: Int = 0,
    val totalWordSearchCompleted: Int = 0,
    val recentResults: List<DailyPuzzleResult> = emptyList(),
    val isPremium: Boolean = false
)

class StatsViewModel(
    repository: GameRepository,
    premiumManager: PremiumManager
) : ViewModel() {
    val uiState: StateFlow<StatsUiState> = combine(
        repository.observeDailyResults(),
        repository.observeWordSearchResults(),
        premiumManager.isPremium
    ) { dailyResults, wordSearchResults, isPremium ->
        StatsUiState(
            currentStreak = StreakCalculator.currentStreak(dailyResults),
            longestStreak = StreakCalculator.longestStreak(dailyResults),
            totalScrambleSolved = dailyResults.count { it.solved },
            totalWordSearchCompleted = wordSearchResults.count { it.completed },
            recentResults = dailyResults.take(30),
            isPremium = isPremium
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StatsUiState())
}
