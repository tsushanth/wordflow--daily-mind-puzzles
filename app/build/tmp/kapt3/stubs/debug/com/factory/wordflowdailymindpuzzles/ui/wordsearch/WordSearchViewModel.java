package com.factory.wordflowdailymindpuzzles.ui.wordsearch;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u001b\u001a\u00020\tH\u0014J\u000e\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u000eJ\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010\u001f\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u000eJ \u0010 \u001a\u00020\t2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0002J\u000e\u0010&\u001a\u00020\t2\u0006\u0010\'\u001a\u00020(J\b\u0010)\u001a\u00020\tH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\f0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012\u00a8\u0006*"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/ui/wordsearch/WordSearchViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;", "premiumManager", "Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;", "(Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;Lcom/factory/wordflowdailymindpuzzles/data/billing/PremiumManager;)V", "_paywallRequests", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/factory/wordflowdailymindpuzzles/ui/wordsearch/WordSearchUiState;", "dragStart", "Lcom/factory/wordflowdailymindpuzzles/domain/GridCell;", "isPremium", "Lkotlinx/coroutines/flow/StateFlow;", "", "()Lkotlinx/coroutines/flow/StateFlow;", "paywallRequests", "Lkotlinx/coroutines/flow/SharedFlow;", "getPaywallRequests", "()Lkotlinx/coroutines/flow/SharedFlow;", "timerJob", "Lkotlinx/coroutines/Job;", "uiState", "getUiState", "onCleared", "onDrag", "cell", "onDragEnd", "onDragStart", "persistCompletion", "elapsedMs", "", "wordsFound", "", "total", "startNewPuzzle", "category", "", "startTimer", "app_debug"})
public final class WordSearchViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.GameRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.factory.wordflowdailymindpuzzles.ui.wordsearch.WordSearchUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.wordsearch.WordSearchUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPremium = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Unit> _paywallRequests = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> paywallRequests = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job timerJob;
    @org.jetbrains.annotations.Nullable()
    private com.factory.wordflowdailymindpuzzles.domain.GridCell dragStart;
    
    public WordSearchViewModel(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.GameRepository repository, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.billing.PremiumManager premiumManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.factory.wordflowdailymindpuzzles.ui.wordsearch.WordSearchUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isPremium() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> getPaywallRequests() {
        return null;
    }
    
    public final void startNewPuzzle(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    private final void startTimer() {
    }
    
    public final void onDragStart(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.domain.GridCell cell) {
    }
    
    public final void onDrag(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.domain.GridCell cell) {
    }
    
    public final void onDragEnd() {
    }
    
    private final void persistCompletion(long elapsedMs, int wordsFound, int total) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}