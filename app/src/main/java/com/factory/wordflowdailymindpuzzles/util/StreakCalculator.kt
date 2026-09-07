package com.factory.wordflowdailymindpuzzles.util

import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object StreakCalculator {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun currentStreak(results: List<DailyPuzzleResult>): Int {
        val solvedDates = results.filter { it.solved }.map { LocalDate.parse(it.date, formatter) }.toSet()
        if (solvedDates.isEmpty()) return 0

        var cursor = LocalDate.now()
        if (cursor !in solvedDates) {
            cursor = cursor.minusDays(1)
            if (cursor !in solvedDates) return 0
        }

        var streak = 0
        while (cursor in solvedDates) {
            streak++
            cursor = cursor.minusDays(1)
        }
        return streak
    }

    fun longestStreak(results: List<DailyPuzzleResult>): Int {
        val solvedDates = results.filter { it.solved }.map { LocalDate.parse(it.date, formatter) }.sorted()
        if (solvedDates.isEmpty()) return 0

        var longest = 1
        var current = 1
        for (i in 1 until solvedDates.size) {
            current = if (solvedDates[i] == solvedDates[i - 1].plusDays(1)) current + 1 else 1
            longest = maxOf(longest, current)
        }
        return longest
    }
}
