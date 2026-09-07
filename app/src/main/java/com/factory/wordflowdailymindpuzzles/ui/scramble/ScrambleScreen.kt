package com.factory.wordflowdailymindpuzzles.ui.scramble

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.factory.wordflowdailymindpuzzles.ui.premium.ProBadge

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScrambleScreen(viewModel: ScrambleViewModel, onRequirePremium: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val isPremium by viewModel.isPremium.collectAsState()
    val hintCredits by viewModel.hintCredits.collectAsState()
    val haptics = LocalHapticFeedback.current

    LaunchedEffect(viewModel) {
        viewModel.paywallRequests.collect { onRequirePremium() }
    }

    LaunchedEffect(state.isSolved, state.message) {
        if (state.isSolved) {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        } else if (state.message != null) {
            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Daily Scramble", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(4.dp))
        Text(
            "Unscramble today's word",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(24.dp))

        when {
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier.semantics { contentDescription = "Loading today's puzzle" }
            )
            state.alreadyPlayedToday || state.isSolved -> ResultCard(state)
            else -> {
                Text("Time: ${state.elapsedSeconds}s", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(16.dp))

                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(state.wordLength) { index ->
                        val tile = state.guessLetters.getOrNull(index)
                        GuessSlot(
                            tile = tile,
                            onClick = {
                                tile?.let {
                                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    viewModel.onGuessTileClick(it)
                                }
                            }
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.availableLetters.forEach { tile ->
                        LetterChip(
                            tile = tile,
                            onClick = {
                                haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                viewModel.onAvailableTileClick(tile)
                            }
                        )
                    }
                }

                state.message?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        it,
                        color = if (state.isSolved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                }

                Spacer(Modifier.height(32.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.shuffleAvailable()
                    }) { Text("Shuffle") }
                    OutlinedButton(onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.useHint()
                    }) {
                        Text("Hint (${state.hintsUsed})")
                        val nextHintLocked = state.hintsUsed >= 1 && !isPremium && hintCredits <= 0
                        if (nextHintLocked) {
                            ProBadge(modifier = Modifier.padding(start = 6.dp))
                        }
                    }
                    Button(onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.submitGuess()
                    }) { Text("Submit") }
                }

                Spacer(Modifier.height(16.dp))
                TextButton(onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.giveUp()
                }) { Text("Give up") }
            }
        }
    }
}

@Composable
private fun ResultCard(state: ScrambleUiState) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (state.isSolved) "Nice work!" else "Come back tomorrow",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))
            Text("Today's word: ${state.revealedWord ?: ""}", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text("Attempts: ${state.attempts}  •  Hints: ${state.hintsUsed}")
        }
    }
}

@Composable
private fun LetterChip(tile: LetterTile, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClickLabel = "Add letter ${tile.char} to your guess", onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(tile.char.toString(), style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
private fun GuessSlot(tile: LetterTile?, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .then(
                if (tile != null) {
                    Modifier.clickable(onClickLabel = "Remove letter ${tile.char}", onClick = onClick)
                } else {
                    Modifier.semantics { contentDescription = "Empty letter slot" }
                }
            ),
        shape = RoundedCornerShape(12.dp),
        color = if (tile != null) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(tile?.char?.toString() ?: "", style = MaterialTheme.typography.titleLarge)
        }
    }
}
