package com.factory.wordflowdailymindpuzzles.ui.wordsearch

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.factory.wordflowdailymindpuzzles.data.WordBank
import com.factory.wordflowdailymindpuzzles.domain.GridCell
import com.factory.wordflowdailymindpuzzles.domain.WordSearchPuzzle
import com.factory.wordflowdailymindpuzzles.ui.premium.ProBadge

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordSearchScreen(viewModel: WordSearchViewModel, onRequirePremium: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val isPremium by viewModel.isPremium.collectAsState()
    val haptics = LocalHapticFeedback.current

    LaunchedEffect(viewModel) {
        viewModel.paywallRequests.collect { onRequirePremium() }
    }

    LaunchedEffect(state.foundWords.size, state.isCompleted) {
        if (state.isCompleted) {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        } else if (state.foundWords.isNotEmpty()) {
            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Word Search", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))

        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WordBank.wordSearchCategories.keys.forEach { category ->
                val isLocked = category != FREE_WORD_SEARCH_CATEGORY && !isPremium
                FilterChip(
                    selected = state.category == category,
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.startNewPuzzle(category)
                    },
                    label = { Text(category) },
                    trailingIcon = { if (isLocked) ProBadge() },
                    modifier = if (isLocked) {
                        Modifier.semantics { contentDescription = "$category, locked, requires Premium" }
                    } else {
                        Modifier
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Time: ${state.elapsedSeconds}s")
        Spacer(Modifier.height(8.dp))

        val puzzle = state.puzzle
        if (puzzle != null) {
            WordGrid(
                puzzle = puzzle,
                foundWords = state.foundWords,
                selection = state.selection,
                onDragStart = { cell ->
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    viewModel.onDragStart(cell)
                },
                onDrag = viewModel::onDrag,
                onDragEnd = viewModel::onDragEnd
            )

            Spacer(Modifier.height(16.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                puzzle.placedWords.forEach { placed ->
                    val found = state.foundWords.contains(placed.word)
                    AssistChip(
                        onClick = {},
                        enabled = false,
                        label = {
                            Text(
                                placed.word,
                                textDecoration = if (found) TextDecoration.LineThrough else null
                            )
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "${placed.word}, ${if (found) "found" else "not found yet"}"
                        }
                    )
                }
            }

            if (state.isCompleted) {
                Spacer(Modifier.height(16.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("All words found!", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.startNewPuzzle(state.category)
                        }) {
                            Text("New Puzzle")
                        }
                    }
                }
            }
        } else {
            Spacer(Modifier.height(48.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "No puzzle loaded",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Pick a category above to start a puzzle.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { viewModel.startNewPuzzle(state.category) }) {
                    Text("Try Again")
                }
            }
        }
    }
}

@Composable
private fun WordGrid(
    puzzle: WordSearchPuzzle,
    foundWords: Set<String>,
    selection: List<GridCell>,
    onDragStart: (GridCell) -> Unit,
    onDrag: (GridCell) -> Unit,
    onDragEnd: () -> Unit
) {
    val foundCells = remember(foundWords, puzzle) {
        puzzle.placedWords.filter { foundWords.contains(it.word) }.flatMap { it.cells }.toSet()
    }
    val selectionSet = selection.toSet()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .semantics {
                contentDescription = "Word search grid. ${foundWords.size} of ${puzzle.placedWords.size} words found. " +
                    "Drag across letters to select a word."
            }
    ) {
        val cellSizePx = with(LocalDensity.current) { (maxWidth / puzzle.size).toPx() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(puzzle) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            onDragStart(offsetToCell(offset, cellSizePx, puzzle.size))
                        },
                        onDrag = { change, _ ->
                            onDrag(offsetToCell(change.position, cellSizePx, puzzle.size))
                        },
                        onDragEnd = { onDragEnd() },
                        onDragCancel = { onDragEnd() }
                    )
                }
        ) {
            for (row in 0 until puzzle.size) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for (col in 0 until puzzle.size) {
                        val cell = GridCell(row, col)
                        val isSelected = selectionSet.contains(cell)
                        val isFound = foundCells.contains(cell)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(
                                    when {
                                        isFound -> MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                                        isSelected -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
                                        else -> Color.Transparent
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                puzzle.letters[row][col].toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun offsetToCell(offset: Offset, cellSizePx: Float, size: Int): GridCell {
    val col = (offset.x / cellSizePx).toInt().coerceIn(0, size - 1)
    val row = (offset.y / cellSizePx).toInt().coerceIn(0, size - 1)
    return GridCell(row, col)
}
