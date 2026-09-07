package com.factory.wordflowdailymindpuzzles.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DailyPuzzleDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: DailyPuzzleDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dailyPuzzleDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    private fun result(
        date: String,
        word: String = "APPLE",
        solved: Boolean = true,
        attempts: Int = 1,
        hintsUsed: Int = 0,
        timeTakenMs: Long = 1000L
    ) = DailyPuzzleResult(date, word, solved, attempts, hintsUsed, timeTakenMs)

    @Test
    fun `getByDate returns null when nothing stored`() = runTest {
        assertNull(dao.getByDate("2026-01-01"))
    }

    @Test
    fun `upsert then getByDate returns the stored result`() = runTest {
        val stored = result("2026-01-01")
        dao.upsert(stored)

        assertEquals(stored, dao.getByDate("2026-01-01"))
    }

    @Test
    fun `upsert with the same date replaces the previous row`() = runTest {
        dao.upsert(result("2026-01-01", solved = false, attempts = 2))
        dao.upsert(result("2026-01-01", solved = true, attempts = 5))

        val fetched = dao.getByDate("2026-01-01")
        assertEquals(true, fetched?.solved)
        assertEquals(5, fetched?.attempts)
    }

    @Test
    fun `observeAll emits inserted rows ordered by date descending`() = runTest {
        dao.observeAll().test {
            assertEquals(emptyList<DailyPuzzleResult>(), awaitItem())

            dao.upsert(result("2026-01-01"))
            assertEquals(listOf(result("2026-01-01")), awaitItem())

            dao.upsert(result("2026-01-03"))
            assertEquals(listOf(result("2026-01-03"), result("2026-01-01")), awaitItem())

            dao.upsert(result("2026-01-02"))
            assertEquals(
                listOf(result("2026-01-03"), result("2026-01-02"), result("2026-01-01")),
                awaitItem()
            )
        }
    }
}
