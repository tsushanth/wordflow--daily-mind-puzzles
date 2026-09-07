package com.factory.wordflowdailymindpuzzles.ui.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import com.factory.wordflowdailymindpuzzles.ui.premium.PremiumGateCard
import com.factory.wordflowdailymindpuzzles.ui.premium.ProBadge

@Composable
fun StatsScreen(viewModel: StatsViewModel, onRequirePremium: () -> Unit) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Your Progress", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(label = "Current Streak", value = state.currentStreak.toString(), modifier = Modifier.weight(1f))
            StatCard(label = "Longest Streak", value = state.longestStreak.toString(), modifier = Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(label = "Scrambles Solved", value = state.totalScrambleSolved.toString(), modifier = Modifier.weight(1f))
            StatCard(label = "Word Searches Done", value = state.totalWordSearchCompleted.toString(), modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(32.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Recent History", style = MaterialTheme.typography.titleMedium)
            if (!state.isPremium && state.recentResults.size > FREE_HISTORY_LIMIT) {
                ProBadge()
            }
        }
        Spacer(Modifier.height(8.dp))

        if (state.recentResults.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "No puzzles solved yet",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Play a Daily Scramble to start building your history.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val visibleResults = if (state.isPremium) state.recentResults else state.recentResults.take(FREE_HISTORY_LIMIT)
            val lockedCount = state.recentResults.size - visibleResults.size

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(visibleResults) { result ->
                    HistoryRow(result)
                }
                if (lockedCount > 0) {
                    item {
                        Spacer(Modifier.height(8.dp))
                        PremiumGateCard(
                            title = "Full History",
                            description = "$lockedCount more entries are waiting - unlock Premium to see your complete puzzle history.",
                            onUnlockClick = onRequirePremium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.clearAndSetSemantics { contentDescription = "$label: $value" }) {
        Column(Modifier.padding(16.dp)) {
            Text(value, style = MaterialTheme.typography.headlineSmall)
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun HistoryRow(result: DailyPuzzleResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clearAndSetSemantics {
                contentDescription = if (result.solved) {
                    "${result.date}: solved, word was ${result.word}"
                } else {
                    "${result.date}: missed"
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(result.date)
        Text(
            if (result.solved) result.word else "Missed",
            color = if (result.solved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
}
