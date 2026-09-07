package com.factory.wordflowdailymindpuzzles.data

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {

    private lateinit var dailyPuzzleDao: DailyPuzzleDao
    private lateinit var wordSearchDao: WordSearchDao
    private lateinit var repository: GameRepository

    private val dailyResult = DailyPuzzleResult(
        date = "2026-01-01",
        word = "APPLE",
        solved = true,
        attempts = 2,
        hintsUsed = 1,
        timeTakenMs = 12000L
    )

    private val wordSearchResult = WordSearchResult(
        id = 1L,
        date = "2026-01-01",
        category = "Animals",
        wordsFound = 8,
        totalWords = 8,
        timeTakenMs = 4000L,
        completed = true
    )

    @Before
    fun setUp() {
        dailyPuzzleDao = mockk()
        wordSearchDao = mockk()
        repository = GameRepository(dailyPuzzleDao, wordSearchDao)
    }

    @Test
    fun `saveDailyResult delegates to the dao`() = runTest {
        coEvery { dailyPuzzleDao.upsert(dailyResult) } returns Unit

        repository.saveDailyResult(dailyResult)

        coVerify(exactly = 1) { dailyPuzzleDao.upsert(dailyResult) }
    }

    @Test
    fun `getDailyResult returns the dao's result`() = runTest {
        coEvery { dailyPuzzleDao.getByDate("2026-01-01") } returns dailyResult

        val result = repository.getDailyResult("2026-01-01")

        assertEquals(dailyResult, result)
    }

    @Test
    fun `getDailyResult returns null when the dao has nothing`() = runTest {
        coEvery { dailyPuzzleDao.getByDate("2026-01-01") } returns null

        assertEquals(null, repository.getDailyResult("2026-01-01"))
    }

    @Test
    fun `observeDailyResults exposes the dao's flow`() = runTest {
        every { dailyPuzzleDao.observeAll() } returns flowOf(listOf(dailyResult))

        repository.observeDailyResults().test {
            assertEquals(listOf(dailyResult), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `saveWordSearchResult delegates to the dao`() = runTest {
        coEvery { wordSearchDao.insert(wordSearchResult) } returns Unit

        repository.saveWordSearchResult(wordSearchResult)

        coVerify(exactly = 1) { wordSearchDao.insert(wordSearchResult) }
    }

    @Test
    fun `observeWordSearchResults exposes the dao's flow`() = runTest {
        every { wordSearchDao.observeAll() } returns flowOf(listOf(wordSearchResult))

        repository.observeWordSearchResults().test {
            assertEquals(listOf(wordSearchResult), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getWordSearchResultsForDate delegates to the dao`() = runTest {
        coEvery { wordSearchDao.getForDate("2026-01-01") } returns listOf(wordSearchResult)

        val result = repository.getWordSearchResultsForDate("2026-01-01")

        assertEquals(listOf(wordSearchResult), result)
    }
}
