package com.factory.wordflowdailymindpuzzles.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.factory.wordflowdailymindpuzzles.WordFlowApplication
import com.factory.wordflowdailymindpuzzles.ui.home.HomeScreen
import com.factory.wordflowdailymindpuzzles.ui.home.HomeViewModel
import com.factory.wordflowdailymindpuzzles.ui.onboarding.OnboardingScreen
import com.factory.wordflowdailymindpuzzles.ui.premium.PaywallScreen
import com.factory.wordflowdailymindpuzzles.ui.premium.PaywallViewModel
import com.factory.wordflowdailymindpuzzles.ui.premium.ProBadge
import com.factory.wordflowdailymindpuzzles.ui.scramble.ScrambleScreen
import com.factory.wordflowdailymindpuzzles.ui.scramble.ScrambleViewModel
import com.factory.wordflowdailymindpuzzles.ui.settings.SettingsScreen
import com.factory.wordflowdailymindpuzzles.ui.settings.SettingsViewModel
import com.factory.wordflowdailymindpuzzles.ui.stats.StatsScreen
import com.factory.wordflowdailymindpuzzles.ui.stats.StatsViewModel
import com.factory.wordflowdailymindpuzzles.ui.wordsearch.WordSearchScreen
import com.factory.wordflowdailymindpuzzles.ui.wordsearch.WordSearchViewModel
import com.factory.wordflowdailymindpuzzles.util.OnboardingPrefs

private val ROUTES_WITHOUT_CHROME = setOf(Destination.Onboarding.route, Destination.Paywall.route)

@Composable
fun WordFlowNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as WordFlowApplication
    val repository = application.repository
    val premiumManager = application.premiumManager
    val billingManager = application.billingManager

    val startDestination = remember {
        if (OnboardingPrefs.hasCompletedOnboarding(context)) Destination.Home.route else Destination.Onboarding.route
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showChrome = currentRoute !in ROUTES_WITHOUT_CHROME
    val isPremium by premiumManager.isPremium.collectAsState()
    val haptics = LocalHapticFeedback.current

    fun openPaywall() {
        navController.navigate(Destination.Paywall.route) { launchSingleTop = true }
    }

    Scaffold(
        topBar = {
            if (showChrome) {
                TopAppBar(
                    title = { Text("WordFlow") },
                    actions = {
                        IconButton(onClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            navController.navigate(Destination.Settings.route)
                        }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showChrome) {
                NavigationBar {
                    bottomNavDestinations.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(iconFor(destination), contentDescription = destination.label) },
                            label = {
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(destination.label)
                                    if (destination == Destination.WordSearch && !isPremium) {
                                        ProBadge()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destination.Onboarding.route) {
                OnboardingScreen(
                    onGetStarted = {
                        OnboardingPrefs.markOnboardingCompleted(context)
                        navController.navigate(Destination.Paywall.route) {
                            popUpTo(Destination.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Destination.Paywall.route) {
                val vm: PaywallViewModel = viewModel(
                    factory = viewModelFactory { initializer { PaywallViewModel(billingManager, premiumManager) } }
                )
                PaywallScreen(
                    viewModel = vm,
                    onClose = {
                        if (!navController.popBackStack()) {
                            navController.navigate(Destination.Home.route) {
                                popUpTo(0)
                            }
                        }
                    }
                )
            }
            composable(Destination.Home.route) {
                val vm: HomeViewModel = viewModel(
                    factory = viewModelFactory { initializer { HomeViewModel(repository) } }
                )
                HomeScreen(
                    viewModel = vm,
                    onNavigateToDaily = { navController.navigate(Destination.Daily.route) },
                    onNavigateToWordSearch = { navController.navigate(Destination.WordSearch.route) }
                )
            }
            composable(Destination.Daily.route) {
                val vm: ScrambleViewModel = viewModel(
                    factory = viewModelFactory { initializer { ScrambleViewModel(repository, premiumManager) } }
                )
                ScrambleScreen(viewModel = vm, onRequirePremium = { openPaywall() })
            }
            composable(Destination.WordSearch.route) {
                val vm: WordSearchViewModel = viewModel(
                    factory = viewModelFactory { initializer { WordSearchViewModel(repository, premiumManager) } }
                )
                WordSearchScreen(viewModel = vm, onRequirePremium = { openPaywall() })
            }
            composable(Destination.Stats.route) {
                val vm: StatsViewModel = viewModel(
                    factory = viewModelFactory { initializer { StatsViewModel(repository, premiumManager) } }
                )
                StatsScreen(viewModel = vm, onRequirePremium = { openPaywall() })
            }
            composable(Destination.Settings.route) {
                val vm: SettingsViewModel = viewModel(
                    factory = viewModelFactory { initializer { SettingsViewModel(billingManager, premiumManager) } }
                )
                SettingsScreen(viewModel = vm, onUpgradeClick = { openPaywall() })
            }
        }
    }
}

private fun iconFor(destination: Destination): ImageVector = when (destination) {
    Destination.Home -> Icons.Filled.Home
    Destination.Daily -> Icons.Filled.EditCalendar
    Destination.WordSearch -> Icons.Filled.Search
    Destination.Stats -> Icons.Filled.BarChart
    else -> Icons.Filled.Home
}
