package com.factory.wordflowdailymindpuzzles.ui.stats

import app.cash.turbine.test
import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.WordSearchResult
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import com.factory.wordflowdailymindpuzzles.util.DateUtils
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class StatsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: GameRepository = mockk()
    private val premiumManager: PremiumManager = mockk()
    private val isPremiumFlow = MutableStateFlow(false)

    private fun dailyResult(date: String, solved: Boolean) = DailyPuzzleResult(
        date = date, word = "APPLE", solved = solved, attempts = 1, hintsUsed = 0, timeTakenMs = 1000L
    )

    private fun wordSearchResult(completed: Boolean) = WordSearchResult(
        date = "2026-01-01", category = "Animals", wordsFound = 8, totalWords = 8,
        timeTakenMs = 4000L, completed = completed
    )

    private fun createViewModel(
        dailyResults: List<DailyPuzzleResult> = emptyList(),
        wordSearchResults: List<WordSearchResult> = emptyList()
    ): StatsViewModel {
        every { repository.observeDailyResults() } returns flowOf(dailyResults)
        every { repository.observeWordSearchResults() } returns flowOf(wordSearchResults)
        every { premiumManager.isPremium } returns isPremiumFlow
        return StatsViewModel(repository, premiumManager)
    }

    @Test
    fun `initial state is default when there is no history`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.uiState.test {
            assertEquals(StatsUiState(), awaitItem())
        }
    }

    @Test
    fun `state aggregates streaks, totals and premium flag`() = runTest(mainDispatcherRule.testDispatcher) {
        val today = DateUtils.todayKey()
        val dailyResults = listOf(dailyResult(today, solved = true), dailyResult("2020-01-01", solved = false))
        val wordSearchResults = listOf(wordSearchResult(completed = true), wordSearchResult(completed = false))
        isPremiumFlow.value = true

        val viewModel = createViewModel(dailyResults, wordSearchResults)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(1, state.currentStreak)
            assertEquals(1, state.longestStreak)
            assertEquals(1, state.totalScrambleSolved)
            assertEquals(1, state.totalWordSearchCompleted)
            assertEquals(dailyResults, state.recentResults)
            assertEquals(true, state.isPremium)
        }
    }

    @Test
    fun `recentResults is capped at 30 entries`() = runTest(mainDispatcherRule.testDispatcher) {
        val dailyResults = (1..40).map { dailyResult("2026-01-%02d".format(it % 28 + 1), solved = true) }

        val viewModel = createViewModel(dailyResults)

        viewModel.uiState.test {
            assertEquals(30, awaitItem().recentResults.size)
        }
    }

    @Test
    fun `state updates when premium status changes`() = runTest(mainDispatcherRule.testDispatcher) {
        val viewModel = createViewModel()

        viewModel.uiState.test {
            assertEquals(false, awaitItem().isPremium)
            isPremiumFlow.value = true
            assertEquals(true, awaitItem().isPremium)
        }
    }
}
