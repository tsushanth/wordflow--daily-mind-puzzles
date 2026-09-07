package com.factory.wordflowdailymindpuzzles.ui.scramble

import app.cash.turbine.test
import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.testutil.MainDispatcherRule
import com.factory.wordflowdailymindpuzzles.util.DateUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/** [DateUtils] is fixed so puzzle selection ("APPLE" at epoch day 0) is deterministic across runs. */
private const val TARGET_WORD = "APPLE"
private const val TODAY_KEY = "2026-01-01"

class ScrambleViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: GameRepository = mockk(relaxed = true)
    private val premiumManager: PremiumManager = mockk(relaxed = true)

    @org.junit.Before
    fun setUp() {
        mockkObject(DateUtils)
        every { DateUtils.todayEpochDay() } returns 0L
        every { DateUtils.todayKey() } returns TODAY_KEY
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel(
        existing: DailyPuzzleResult? = null,
        isPremium: Boolean = false,
        hintCredits: Int = 0,
        consumeHintCredit: Boolean = false
    ): ScrambleViewModel {
        coEvery { repository.getDailyResult(TODAY_KEY) } returns existing
        every { premiumManager.isPremium } returns MutableStateFlow(isPremium)
        every { premiumManager.hintCredits } returns MutableStateFlow(hintCredits)
        every { premiumManager.consumeHintCredit() } returns consumeHintCredit
        val viewModel = ScrambleViewModel(repository, premiumManager)
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        return viewModel
    }

    /** Clicks tiles (by character, order-independent) to spell [word] into the guess row. */
    private fun buildGuess(viewModel: ScrambleViewModel, word: String) {
        val pool = viewModel.uiState.value.availableLetters.toMutableList()
        for (c in word) {
            val tile = pool.first { it.char == c }
            pool.remove(tile)
            viewModel.onAvailableTileClick(tile)
        }
    }

    @Test
    fun `loads a scrambled puzzle when nothing was played today`() {
        val viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.alreadyPlayedToday)
        assertEquals(TARGET_WORD.length, state.wordLength)
        assertEquals(TARGET_WORD.length, state.availableLetters.size)
        assertTrue(state.guessLetters.isEmpty())
        assertEquals(
            TARGET_WORD.toList().sorted(),
            state.availableLetters.map { it.char }.sorted()
        )
    }

    @Test
    fun `shows the previous result when already played today`() {
        val existing = DailyPuzzleResult(
            date = TODAY_KEY, word = TARGET_WORD, solved = true, attempts = 3, hintsUsed = 1, timeTakenMs = 5000L
        )
        val viewModel = createViewModel(existing = existing)

        val state = viewModel.uiState.value
        assertTrue(state.alreadyPlayedToday)
        assertTrue(state.isSolved)
        assertEquals(TARGET_WORD, state.revealedWord)
        assertEquals(3, state.attempts)
        assertEquals(1, state.hintsUsed)
    }

    @Test
    fun `clicking an available tile moves it into the guess`() {
        val viewModel = createViewModel()
        val tile = viewModel.uiState.value.availableLetters.first()

        viewModel.onAvailableTileClick(tile)

        val state = viewModel.uiState.value
        assertEquals(listOf(tile), state.guessLetters)
        assertFalse(state.availableLetters.contains(tile))
        assertEquals(TARGET_WORD.length - 1, state.availableLetters.size)
    }

    @Test
    fun `clicking a guess tile moves it back to available`() {
        val viewModel = createViewModel()
        val tile = viewModel.uiState.value.availableLetters.first()
        viewModel.onAvailableTileClick(tile)

        viewModel.onGuessTileClick(tile)

        val state = viewModel.uiState.value
        assertTrue(state.guessLetters.isEmpty())
        assertEquals(TARGET_WORD.length, state.availableLetters.size)
    }

    @Test
    fun `shuffleAvailable preserves the same letters`() {
        val viewModel = createViewModel()
        val before = viewModel.uiState.value.availableLetters.map { it.char }.sorted()

        viewModel.shuffleAvailable()

        assertEquals(before, viewModel.uiState.value.availableLetters.map { it.char }.sorted())
    }

    @Test
    fun `useHint within the free limit does not touch premium or credits`() {
        val viewModel = createViewModel(isPremium = false, hintCredits = 0)

        viewModel.useHint()

        assertEquals(1, viewModel.uiState.value.hintsUsed)
        assertEquals(TARGET_WORD[0], viewModel.uiState.value.guessLetters[0].char)
        verify(exactly = 0) { premiumManager.consumeHintCredit() }
    }

    @Test
    fun `useHint beyond the free limit requests the paywall when no credits and not premium`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val viewModel = createViewModel(isPremium = false, hintCredits = 0, consumeHintCredit = false)
            viewModel.useHint()

            viewModel.paywallRequests.test {
                viewModel.useHint()
                awaitItem()
            }
            assertEquals(1, viewModel.uiState.value.hintsUsed)
        }

    @Test
    fun `useHint beyond the free limit consumes a credit when available`() {
        val viewModel = createViewModel(isPremium = false, hintCredits = 1, consumeHintCredit = true)
        viewModel.useHint()

        viewModel.useHint()

        assertEquals(2, viewModel.uiState.value.hintsUsed)
        verify(exactly = 1) { premiumManager.consumeHintCredit() }
    }

    @Test
    fun `useHint beyond the free limit is free for premium users`() {
        val viewModel = createViewModel(isPremium = true)
        viewModel.useHint()

        viewModel.useHint()

        assertEquals(2, viewModel.uiState.value.hintsUsed)
        verify(exactly = 0) { premiumManager.consumeHintCredit() }
    }

    @Test
    fun `useHint never exceeds word length minus one`() {
        val viewModel = createViewModel(isPremium = true)

        repeat(10) { viewModel.useHint() }

        assertEquals(TARGET_WORD.length - 1, viewModel.uiState.value.hintsUsed)
    }

    @Test
    fun `submitGuess with an incomplete selection shows a message without counting an attempt`() {
        val viewModel = createViewModel()
        viewModel.onAvailableTileClick(viewModel.uiState.value.availableLetters.first())

        viewModel.submitGuess()

        val state = viewModel.uiState.value
        assertEquals("Use all the letters!", state.message)
        assertEquals(0, state.attempts)
        assertFalse(state.isSolved)
    }

    @Test
    fun `submitGuess with the correct word marks the puzzle solved and persists it`() {
        val viewModel = createViewModel()
        buildGuess(viewModel, TARGET_WORD)

        viewModel.submitGuess()
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.isSolved)
        assertEquals("Solved!", state.message)
        assertEquals(TARGET_WORD, state.revealedWord)
        assertEquals(1, state.attempts)
        coVerify(exactly = 1) {
            repository.saveDailyResult(match { it.date == TODAY_KEY && it.word == TARGET_WORD && it.solved && it.attempts == 1 })
        }
    }

    @Test
    fun `submitGuess with the wrong word resets the guess and counts an attempt`() {
        val viewModel = createViewModel()
        buildGuess(viewModel, TARGET_WORD.reversed())

        viewModel.submitGuess()

        val state = viewModel.uiState.value
        assertFalse(state.isSolved)
        assertEquals("Not quite, try again.", state.message)
        assertEquals(1, state.attempts)
        assertTrue(state.guessLetters.isEmpty())
        assertEquals(TARGET_WORD.length, state.availableLetters.size)
    }

    @Test
    fun `giveUp reveals the word and persists an unsolved result`() {
        val viewModel = createViewModel()

        viewModel.giveUp()
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        val state = viewModel.uiState.value
        assertTrue(state.isSolved)
        assertEquals(TARGET_WORD, state.revealedWord)
        assertEquals("The word was $TARGET_WORD", state.message)
        coVerify(exactly = 1) {
            repository.saveDailyResult(match { !it.solved && it.word == TARGET_WORD })
        }
    }

    @Test
    fun `interactions are ignored once the puzzle is solved`() {
        val viewModel = createViewModel()
        buildGuess(viewModel, TARGET_WORD)
        viewModel.submitGuess()
        val solvedState = viewModel.uiState.value

        viewModel.shuffleAvailable()
        viewModel.useHint()

        assertEquals(solvedState, viewModel.uiState.value)
    }
}
