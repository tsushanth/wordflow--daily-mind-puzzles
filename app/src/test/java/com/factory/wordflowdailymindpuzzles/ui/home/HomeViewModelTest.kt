package com.factory.wordflowdailymindpuzzles.ui.home

import app.cash.turbine.test
import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import com.factory.wordflowdailymindpuzzles.util.DateUtils
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: GameRepository = mockk()

    private fun result(date: String, solved: Boolean) = DailyPuzzleResult(
        date = date,
        word = "APPLE",
        solved = solved,
        attempts = 1,
        hintsUsed = 0,
        timeTakenMs = 1000L
    )

    @Test
    fun `initial state is default when no results exist`() = runTest(mainDispatcherRule.testDispatcher) {
        every { repository.observeDailyResults() } returns flowOf(emptyList())
        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            assertEquals(HomeUiState(), awaitItem())
        }
    }

    @Test
    fun `state reflects streaks and totals from solved results`() = runTest(mainDispatcherRule.testDispatcher) {
        val today = DateUtils.todayKey()
        val yesterday = java.time.LocalDate.now().minusDays(1).toString()
        val results = listOf(
            result(today, solved = true),
            result(yesterday, solved = true),
            result("2020-01-01", solved = false)
        )
        every { repository.observeDailyResults() } returns flowOf(results)

        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(2, state.currentStreak)
            assertEquals(2, state.longestStreak)
            assertEquals(true, state.solvedToday)
            assertEquals(2, state.totalSolved)
        }
    }

    @Test
    fun `solvedToday is false when today's puzzle is unsolved`() = runTest(mainDispatcherRule.testDispatcher) {
        val today = DateUtils.todayKey()
        every { repository.observeDailyResults() } returns flowOf(listOf(result(today, solved = false)))

        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            assertEquals(false, awaitItem().solvedToday)
        }
    }

    @Test
    fun `state updates as the underlying flow emits new results`() = runTest(mainDispatcherRule.testDispatcher) {
        val today = DateUtils.todayKey()
        every { repository.observeDailyResults() } returns kotlinx.coroutines.flow.flow {
            emit(emptyList())
            emit(listOf(result(today, solved = true)))
        }

        val viewModel = HomeViewModel(repository)

        viewModel.uiState.test {
            assertEquals(0, awaitItem().totalSolved)
            assertEquals(1, awaitItem().totalSolved)
        }
    }
}
