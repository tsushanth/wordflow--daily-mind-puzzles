package com.factory.wordflowdailymindpuzzles.ui.scramble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult
import com.factory.wordflowdailymindpuzzles.data.GameRepository
import com.factory.wordflowdailymindpuzzles.data.WordBank
import com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager
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
import kotlin.random.Random

/** Hints beyond this count per puzzle require Premium or a spent hint credit. */
private const val FREE_HINT_LIMIT = 1

data class LetterTile(val id: Int, val char: Char)

data class ScrambleUiState(
    val isLoading: Boolean = true,
    val availableLetters: List<LetterTile> = emptyList(),
    val guessLetters: List<LetterTile> = emptyList(),
    val wordLength: Int = 0,
    val hintsUsed: Int = 0,
    val attempts: Int = 0,
    val elapsedSeconds: Int = 0,
    val isSolved: Boolean = false,
    val alreadyPlayedToday: Boolean = false,
    val revealedWord: String? = null,
    val message: String? = null
)

class ScrambleViewModel(
    private val repository: GameRepository,
    private val premiumManager: PremiumManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScrambleUiState())
    val uiState: StateFlow<ScrambleUiState> = _uiState.asStateFlow()

    val isPremium: StateFlow<Boolean> = premiumManager.isPremium
    val hintCredits: StateFlow<Int> = premiumManager.hintCredits

    private val _paywallRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val paywallRequests: SharedFlow<Unit> = _paywallRequests.asSharedFlow()

    private lateinit var targetWord: String
    private var nextTileId = 0
    private var timerJob: Job? = null
    private val todayKey = DateUtils.todayKey()

    init {
        loadPuzzle()
    }

    private fun loadPuzzle() {
        val epochDay = DateUtils.todayEpochDay()
        val index = (epochDay % WordBank.scrambleWords.size).toInt()
        targetWord = WordBank.scrambleWords[index]

        viewModelScope.launch {
            val existing = repository.getDailyResult(todayKey)
            if (existing != null) {
                _uiState.value = ScrambleUiState(
                    isLoading = false,
                    alreadyPlayedToday = true,
                    isSolved = existing.solved,
                    revealedWord = targetWord,
                    wordLength = targetWord.length,
                    attempts = existing.attempts,
                    hintsUsed = existing.hintsUsed
                )
            } else {
                setupLetters()
                startTimer()
            }
        }
    }

    private fun setupLetters() {
        val random = Random(DateUtils.todayEpochDay())
        var shuffled: List<Char>
        do {
            shuffled = targetWord.toList().shuffled(random)
        } while (shuffled.joinToString("") == targetWord)

        val tiles = shuffled.map { c -> LetterTile(nextTileId++, c) }
        _uiState.value = ScrambleUiState(
            isLoading = false,
            availableLetters = tiles,
            wordLength = targetWord.length
        )
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_uiState.value.isSolved || _uiState.value.alreadyPlayedToday) break
                _uiState.value = _uiState.value.copy(elapsedSeconds = _uiState.value.elapsedSeconds + 1)
            }
        }
    }

    fun onAvailableTileClick(tile: LetterTile) {
        val state = _uiState.value
        if (state.isSolved || state.alreadyPlayedToday) return
        _uiState.value = state.copy(
            availableLetters = state.availableLetters.filterNot { it.id == tile.id },
            guessLetters = state.guessLetters + tile,
            message = null
        )
    }

    fun onGuessTileClick(tile: LetterTile) {
        val state = _uiState.value
        if (state.isSolved || state.alreadyPlayedToday) return
        _uiState.value = state.copy(
            guessLetters = state.guessLetters.filterNot { it.id == tile.id },
            availableLetters = state.availableLetters + tile,
            message = null
        )
    }

    fun shuffleAvailable() {
        val state = _uiState.value
        if (state.isSolved || state.alreadyPlayedToday) return
        _uiState.value = state.copy(availableLetters = state.availableLetters.shuffled())
    }

    fun useHint() {
        val state = _uiState.value
        if (state.isSolved || state.alreadyPlayedToday) return
        val maxHints = targetWord.length - 1
        if (state.hintsUsed >= maxHints) return

        val newHints = state.hintsUsed + 1
        if (newHints > FREE_HINT_LIMIT && !premiumManager.isPremium.value) {
            if (!premiumManager.consumeHintCredit()) {
                _paywallRequests.tryEmit(Unit)
                return
            }
        }
        val pool = (state.availableLetters + state.guessLetters).toMutableList()
        val newGuess = mutableListOf<LetterTile>()
        for (i in 0 until newHints) {
            val target = targetWord[i]
            val tile = pool.first { it.char == target }
            pool.remove(tile)
            newGuess.add(tile)
        }

        _uiState.value = state.copy(
            guessLetters = newGuess,
            availableLetters = pool,
            hintsUsed = newHints,
            message = null
        )
    }

    fun submitGuess() {
        val state = _uiState.value
        if (state.isSolved || state.alreadyPlayedToday) return
        if (state.guessLetters.size != targetWord.length) {
            _uiState.value = state.copy(message = "Use all the letters!")
            return
        }

        val guessWord = state.guessLetters.joinToString("") { it.char.toString() }
        val newAttempts = state.attempts + 1

        if (guessWord == targetWord) {
            timerJob?.cancel()
            _uiState.value = state.copy(
                isSolved = true,
                attempts = newAttempts,
                revealedWord = targetWord,
                message = "Solved!"
            )
            persistResult(solved = true, attempts = newAttempts, elapsedMs = state.elapsedSeconds * 1000L)
        } else {
            _uiState.value = state.copy(
                attempts = newAttempts,
                availableLetters = (state.availableLetters + state.guessLetters).shuffled(),
                guessLetters = emptyList(),
                message = "Not quite, try again."
            )
        }
    }

    fun giveUp() {
        val state = _uiState.value
        if (state.isSolved || state.alreadyPlayedToday) return
        timerJob?.cancel()
        _uiState.value = state.copy(
            isSolved = true,
            revealedWord = targetWord,
            message = "The word was $targetWord"
        )
        persistResult(solved = false, attempts = state.attempts, elapsedMs = state.elapsedSeconds * 1000L)
    }

    private fun persistResult(solved: Boolean, attempts: Int, elapsedMs: Long) {
        viewModelScope.launch {
            repository.saveDailyResult(
                DailyPuzzleResult(
                    date = todayKey,
                    word = targetWord,
                    solved = solved,
                    attempts = attempts,
                    hintsUsed = _uiState.value.hintsUsed,
                    timeTakenMs = elapsedMs
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
