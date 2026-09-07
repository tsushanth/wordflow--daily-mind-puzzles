package com.factory.wordflowdailymindpuzzles.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordSearchDao {
    @Insert
    suspend fun insert(result: WordSearchResult)

    @Query("SELECT * FROM word_search_results ORDER BY id DESC")
    fun observeAll(): Flow<List<WordSearchResult>>

    @Query("SELECT * FROM word_search_results WHERE date = :date ORDER BY id DESC")
    suspend fun getForDate(date: String): List<WordSearchResult>
}
