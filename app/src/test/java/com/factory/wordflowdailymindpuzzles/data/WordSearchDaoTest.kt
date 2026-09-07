package com.factory.wordflowdailymindpuzzles.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordSearchDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: WordSearchDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.wordSearchDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun result(
        date: String,
        category: String = "Animals",
        wordsFound: Int = 8,
        totalWords: Int = 8,
        timeTakenMs: Long = 5000L,
        completed: Boolean = true
    ) = WordSearchResult(
        date = date,
        category = category,
        wordsFound = wordsFound,
        totalWords = totalWords,
        timeTakenMs = timeTakenMs,
        completed = completed
    )

    @Test
    fun `getForDate returns empty list when nothing stored`() = runTest {
        assertTrue(dao.getForDate("2026-01-01").isEmpty())
    }

    @Test
    fun `insert then getForDate returns rows for that date only`() = runTest {
        dao.insert(result("2026-01-01", category = "Animals"))
        dao.insert(result("2026-01-01", category = "Nature"))
        dao.insert(result("2026-01-02", category = "Food"))

        val forDate = dao.getForDate("2026-01-01")

        assertEquals(2, forDate.size)
        assertTrue(forDate.all { it.date == "2026-01-01" })
    }

    @Test
    fun `insert autogenerates ascending ids`() = runTest {
        dao.insert(result("2026-01-01"))
        dao.insert(result("2026-01-02"))

        val stored = dao.getForDate("2026-01-02").single()
        assertTrue(stored.id > dao.getForDate("2026-01-01").single().id)
    }

    @Test
    fun `observeAll emits rows ordered by id descending`() = runTest {
        dao.observeAll().test {
            assertEquals(emptyList<WordSearchResult>(), awaitItem())

            dao.insert(result("2026-01-01", category = "Animals"))
            val afterFirst = awaitItem()
            assertEquals(1, afterFirst.size)
            assertEquals("Animals", afterFirst.first().category)

            dao.insert(result("2026-01-02", category = "Nature"))
            val afterSecond = awaitItem()
            assertEquals(listOf("Nature", "Animals"), afterSecond.map { it.category })
        }
    }
}
