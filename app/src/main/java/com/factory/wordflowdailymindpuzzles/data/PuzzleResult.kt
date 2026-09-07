package com.factory.wordflowdailymindpuzzles.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_puzzle_results")
data class DailyPuzzleResult(
    @PrimaryKey val date: String,
    val word: String,
    val solved: Boolean,
    val attempts: Int,
    val hintsUsed: Int,
    val timeTakenMs: Long
)
