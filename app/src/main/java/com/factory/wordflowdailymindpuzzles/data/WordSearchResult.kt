package com.factory.wordflowdailymindpuzzles.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_search_results")
data class WordSearchResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val category: String,
    val wordsFound: Int,
    val totalWords: Int,
    val timeTakenMs: Long,
    val completed: Boolean
)
