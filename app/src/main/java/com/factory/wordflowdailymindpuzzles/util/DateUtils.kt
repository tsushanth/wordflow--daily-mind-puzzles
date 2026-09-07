package com.factory.wordflowdailymindpuzzles.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun todayKey(): String = LocalDate.now().format(formatter)

    fun todayEpochDay(): Long = LocalDate.now().toEpochDay()
}
