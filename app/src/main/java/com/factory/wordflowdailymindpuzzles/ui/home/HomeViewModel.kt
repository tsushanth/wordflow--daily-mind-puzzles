package com.factory.wordflowdailymindpuzzles.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.util.DateUtils
import com.factory.wordflowdailymindpuzzles.util.StreakCalculator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val solvedToday: Boolean = false,
    val totalSolved: Int = 0
)

class HomeViewModel(repository: GameRepository) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = repository.observeDailyResults()
        .map { results ->
            HomeUiState(
                currentStreak = StreakCalculator.currentStreak(results),
                longestStreak = StreakCalculator.longestStreak(results),
                solvedToday = results.any { it.date == DateUtils.todayKey() && it.solved },
                totalSolved = results.count { it.solved }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}
