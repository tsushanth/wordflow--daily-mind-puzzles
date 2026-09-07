package com.factory.wordflowdailymindpuzzles.ui.wordsearch

import app.cash.turbine.test
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.WordBank
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.domain.PlacedWord
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WordSearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: GameRepository = mockk(relaxed = true)
    private val premiumManager: PremiumManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        coEvery { repository.saveWordSearchResult(any()) } returns Unit
    }

    private fun createViewModel(isPremium: Boolean = false): WordSearchViewModel {
        every { premiumManager.isPremium } returns MutableStateFlow(isPremium)
        val viewModel = WordSearchViewModel(repository, premiumManager)
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        return viewModel
    }

    private fun completeWord(viewModel: WordSearchViewModel, placed: PlacedWord) {
        viewModel.onDragStart(placed.cells.first())
        viewModel.onDrag(placed.cells.last())
        viewModel.onDragEnd()
    }

    @Test
    fun `loads a puzzle for the free category on start`() {
        val viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertEquals(FREE_WORD_SEARCH_CATEGORY, state.category)
        assertTrue(state.puzzle != null)
        assertTrue(state.foundWords.isEmpty())
        assertFalse(state.isCompleted)
    }

    @Test
    fun `starting a locked category without premium requests the paywall and keeps the puzzle`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel = createViewModel(isPremium = false)
            val lockedCategory = WordBank.wordSearchCategories.keys.first { it != FREE_WORD_SEARCH_CATEGORY }
            val stateBefore = viewModel.uiState.value

            viewModel.paywallRequests.test {
                viewModel.startNewPuzzle(lockedCategory)
                awaitItem()
            }

            assertEquals(stateBefore, viewModel.uiState.value)
        }

    @Test
    fun `starting a locked category with premium loads that category's puzzle`() {
        val viewModel = createViewModel(isPremium = true)
        val unlockedCategory = WordBank.wordSearchCategories.keys.first { it != FREE_WORD_SEARCH_CATEGORY }

        viewModel.startNewPuzzle(unlockedCategory)

        val state = viewModel.uiState.value
        assertEquals(unlockedCategory, state.category)
        assertTrue(state.foundWords.isEmpty())
        assertFalse(state.isCompleted)
    }

    @Test
    fun `dragging along a placed word marks it found`() {
        val viewModel = createViewModel()
        val puzzle = viewModel.uiState.value.puzzle!!
        val word = puzzle.placedWords.first()

        completeWord(viewModel, word)

        val state = viewModel.uiState.value
        assertTrue(state.foundWords.contains(word.word))
        assertTrue(state.selection.isEmpty())
    }

    @Test
    fun `dragging a selection that matches no word clears the selection`() {
        val viewModel = createViewModel()
        val puzzle = viewModel.uiState.value.puzzle!!
        val corner = puzzle.placedWords.flatMap { it.cells }.toSet()
        val emptyCell = (0 until puzzle.size).flatMap { r -> (0 until puzzle.size).map { c -> com.factory.wordflowdailymindpuzzles.domain.GridCell(r, c) } }
            .first { it !in corner }

        viewModel.onDragStart(emptyCell)
        viewModel.onDrag(emptyCell)
        viewModel.onDragEnd()

        assertTrue(viewModel.uiState.value.foundWords.isEmpty())
        assertTrue(viewModel.uiState.value.selection.isEmpty())
    }

    @Test
    fun `finding every word completes the puzzle and persists the result`() {
        val viewModel = createViewModel()
        val puzzle = viewModel.uiState.value.puzzle!!

        puzzle.placedWords.forEach { completeWord(viewModel, it) }
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.isCompleted)
        assertEquals(puzzle.placedWords.size, state.foundWords.size)
        coVerify(exactly = 1) {
            repository.saveWordSearchResult(match {
                it.completed && it.wordsFound == puzzle.placedWords.size && it.totalWords == puzzle.placedWords.size
            })
        }
    }
}
