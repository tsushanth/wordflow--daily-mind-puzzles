package com.factory.wordflowdailymindpuzzles.ui.navigation

sealed class Destination(val route: String, val label: String) {
    data object Home : Destination("home", "Home")
    data object Daily : Destination("daily", "Daily")
    data object WordSearch : Destination("word_search", "Search")
    data object Stats : Destination("stats", "Stats")
    data object Onboarding : Destination("onboarding", "Welcome")
    data object Paywall : Destination("paywall", "Premium")
    data object Settings : Destination("settings", "Settings")
}

val bottomNavDestinations = listOf(Destination.Home, Destination.Daily, Destination.WordSearch, Destination.Stats)
