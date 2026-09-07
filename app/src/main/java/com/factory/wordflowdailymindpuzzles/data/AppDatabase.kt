package com.factory.wordflowdailymindpuzzles.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DailyPuzzleResult::class, WordSearchResult::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyPuzzleDao(): DailyPuzzleDao
    abstract fun wordSearchDao(): WordSearchDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wordflow.db"
                ).build().also { instance = it }
            }
    }
}
