package com.factory.wordflowdailymindpuzzles.ui.wordsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.WordBank
import com.factory.wordflowdailymindpuzzles.data.WordSearchResult
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
import com.factory.wordflowdailymindpuzzles.domain.GridCell
import com.factory.wordflowdailymindpuzzles.domain.WordSearchEngine
import com.factory.wordflowdailymindpuzzles.domain.WordSearchPuzzle
import com.factory.wordflowdailymindpuzzles.util.DateUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Only the first category is free to play; the rest require Premium. */
val FREE_WORD_SEARCH_CATEGORY: String = WordBank.wordSearchCategories.keys.first()

data class WordSearchUiState(
    val category: String = FREE_WORD_SEARCH_CATEGORY,
    val puzzle: WordSearchPuzzle? = null,
    val foundWords: Set<String> = emptySet(),
    val selection: List<GridCell> = emptyList(),
    val elapsedSeconds: Int = 0,
    val isCompleted: Boolean = false
)

class WordSearchViewModel(
    private val repository: GameRepository,
    private val premiumManager: PremiumManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(WordSearchUiState())
    val uiState: StateFlow<WordSearchUiState> = _uiState.asStateFlow()

    val isPremium: StateFlow<Boolean> = premiumManager.isPremium

    private val _paywallRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val paywallRequests: SharedFlow<Unit> = _paywallRequests.asSharedFlow()

    private var timerJob: Job? = null
    private var dragStart: GridCell? = null

    init {
        startNewPuzzle(_uiState.value.category)
    }

    fun startNewPuzzle(category: String) {
        if (category != FREE_WORD_SEARCH_CATEGORY && !premiumManager.isPremium.value) {
            _paywallRequests.tryEmit(Unit)
            return
        }
        val words = WordBank.wordSearchCategories.getValue(category)
        val puzzle = WordSearchEngine.generate(words, size = 10)
        timerJob?.cancel()
        _uiState.value = WordSearchUiState(category = category, puzzle = puzzle)
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_uiState.value.isCompleted) break
                _uiState.value = _uiState.value.copy(elapsedSeconds = _uiState.value.elapsedSeconds + 1)
            }
        }
    }

    fun onDragStart(cell: GridCell) {
        if (_uiState.value.isCompleted) return
        dragStart = cell
        _uiState.value = _uiState.value.copy(selection = listOf(cell))
    }

    fun onDrag(cell: GridCell) {
        val start = dragStart ?: return
        val puzzle = _uiState.value.puzzle ?: return
        val snapped = WordSearchEngine.snapToLine(start, cell, puzzle.size)
        val line = WordSearchEngine.cellsAlongLine(start, snapped)
        _uiState.value = _uiState.value.copy(selection = line)
    }

    fun onDragEnd() {
        val state = _uiState.value
        val puzzle = state.puzzle ?: return
        val selectedSet = state.selection.toSet()

        val match = puzzle.placedWords.firstOrNull { placed ->
            !state.foundWords.contains(placed.word) && placed.cells.toSet() == selectedSet
        }

        if (match != null) {
            val newFound = state.foundWords + match.word
            val allFound = newFound.size == puzzle.placedWords.size
            _uiState.value = state.copy(
                foundWords = newFound,
                selection = emptyList(),
                isCompleted = allFound
            )
            if (allFound) {
                timerJob?.cancel()
                persistCompletion(
                    elapsedMs = state.elapsedSeconds * 1000L,
                    wordsFound = newFound.size,
                    total = puzzle.placedWords.size
                )
            }
        } else {
            _uiState.value = state.copy(selection = emptyList())
        }
        dragStart = null
    }

    private fun persistCompletion(elapsedMs: Long, wordsFound: Int, total: Int) {
        viewModelScope.launch {
            repository.saveWordSearchResult(
                WordSearchResult(
                    date = DateUtils.todayKey(),
                    category = _uiState.value.category,
                    wordsFound = wordsFound,
                    totalWords = total,
                    timeTakenMs = elapsedMs,
                    completed = true
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
