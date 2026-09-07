package com.factory.wordflowdailymindpuzzles.data

import kotlinx.coroutines.flow.Flow

class GameRepository(
    private val dailyPuzzleDao: DailyPuzzleDao,
    private val wordSearchDao: WordSearchDao
) {
    suspend fun saveDailyResult(result: DailyPuzzleResult) = dailyPuzzleDao.upsert(result)

    suspend fun getDailyResult(date: String): DailyPuzzleResult? = dailyPuzzleDao.getByDate(date)

    fun observeDailyResults(): Flow<List<DailyPuzzleResult>> = dailyPuzzleDao.observeAll()

    suspend fun saveWordSearchResult(result: WordSearchResult) = wordSearchDao.insert(result)

    fun observeWordSearchResults(): Flow<List<WordSearchResult>> = wordSearchDao.observeAll()

    suspend fun getWordSearchResultsForDate(date: String): List<WordSearchResult> =
        wordSearchDao.getForDate(date)
}
