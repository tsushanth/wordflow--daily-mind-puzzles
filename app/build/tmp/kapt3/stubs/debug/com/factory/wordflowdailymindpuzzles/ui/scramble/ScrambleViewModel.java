package com.factory.wordflowdailymindpuzzles.ui.scramble;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010 \u001a\u00020\tJ\b\u0010!\u001a\u00020\tH\u0002J\u000e\u0010\"\u001a\u00020\t2\u0006\u0010#\u001a\u00020$J\b\u0010%\u001a\u00020\tH\u0014J\u000e\u0010&\u001a\u00020\t2\u0006\u0010#\u001a\u00020$J \u0010\'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020\tH\u0002J\u0006\u0010-\u001a\u00020\tJ\b\u0010.\u001a\u00020\tH\u0002J\u0006\u0010/\u001a\u00020\tJ\u0006\u00100\u001a\u00020\tR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0011\u00a8\u00061"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/ui/scramble/ScrambleViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;", "premiumManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "(Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;)V", "_paywallRequests", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/factory/wordflowdailymindpuzzles/ui/scramble/ScrambleUiState;", "hintCredits", "Lkotlinx/coroutines/flow/StateFlow;", "", "getHintCredits", "()Lkotlinx/coroutines/flow/StateFlow;", "isPremium", "", "nextTileId", "paywallRequests", "Lkotlinx/coroutines/flow/SharedFlow;", "getPaywallRequests", "()Lkotlinx/coroutines/flow/SharedFlow;", "targetWord", "", "timerJob", "Lkotlinx/coroutines/Job;", "todayKey", "uiState", "getUiState", "giveUp", "loadPuzzle", "onAvailableTileClick", "tile", "Lcom/factory/wordflowdailymindpuzzles/ui/scramble/LetterTile;", "onCleared", "onGuessTileClick", "persistResult", "solved", "attempts", "elapsedMs", "", "setupLetters", "shuffleAvailable", "startTimer", "submitGuess", "useHint", "app_debug"})
public final class ScrambleViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.GameRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.factory.wordflowdailymindpuzzles.ui.scramble.ScrambleUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.scramble.ScrambleUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPremium = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> hintCredits = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Unit> _paywallRequests = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> paywallRequests = null;
    private java.lang.String targetWord;
    private int nextTileId = 0;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job timerJob;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String todayKey = null;
    
    public ScrambleViewModel(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.GameRepository repository, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.scramble.ScrambleUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPremium() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getHintCredits() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> getPaywallRequests() {
        return null;
    }
    
    private final void loadPuzzle() {
    }
    
    private final void setupLetters() {
    }
    
    private final void startTimer() {
    }
    
    public final void onAvailableTileClick(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.ui.scramble.LetterTile tile) {
    }
    
    public final void onGuessTileClick(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.ui.scramble.LetterTile tile) {
    }
    
    public final void shuffleAvailable() {
    }
    
    public final void useHint() {
    }
    
    public final void submitGuess() {
    }
    
    public final void giveUp() {
    }
    
    private final void persistResult(boolean solved, int attempts, long elapsedMs) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}