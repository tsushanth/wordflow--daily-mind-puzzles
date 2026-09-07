package com.factory.wordflowdailymindpuzzles.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyPuzzleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(result: DailyPuzzleResult)

    @Query("SELECT * FROM daily_puzzle_results WHERE date = :date LIMIT 1")
    suspend fun getByDate(date: String): DailyPuzzleResult?

    @Query("SELECT * FROM daily_puzzle_results ORDER BY date DESC")
    fun observeAll(): Flow<List<DailyPuzzleResult>>
}
