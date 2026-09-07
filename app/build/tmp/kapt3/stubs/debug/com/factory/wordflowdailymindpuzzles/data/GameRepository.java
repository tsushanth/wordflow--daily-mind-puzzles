package com.factory.wordflowdailymindpuzzles.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\r0\u0010J\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0010J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/GameRepository;", "", "dailyPuzzleDao", "Lcom/factory/wordflowdailymindpuzzles/data/DailyPuzzleDao;", "wordSearchDao", "Lcom/factory/wordflowdailymindpuzzles/data/WordSearchDao;", "(Lcom/factory/wordflowdailymindpuzzles/data/DailyPuzzleDao;Lcom/factory/wordflowdailymindpuzzles/data/WordSearchDao;)V", "getDailyResult", "Lcom/factory/wordflowdailymindpuzzles/data/DailyPuzzleResult;", "date", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWordSearchResultsForDate", "", "Lcom/factory/wordflowdailymindpuzzles/data/WordSearchResult;", "observeDailyResults", "Lkotlinx/coroutines/flow/Flow;", "observeWordSearchResults", "saveDailyResult", "", "result", "(Lcom/factory/wordflowdailymindpuzzles/data/DailyPuzzleResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveWordSearchResult", "(Lcom/factory/wordflowdailymindpuzzles/data/WordSearchResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GameRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.DailyPuzzleDao dailyPuzzleDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.factory.wordflowdailymindpuzzles.data.WordSearchDao wordSearchDao = null;
    
    public GameRepository(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.DailyPuzzleDao dailyPuzzleDao, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.WordSearchDao wordSearchDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveDailyResult(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDailyResult(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult>> observeDailyResults() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveWordSearchResult(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.WordSearchResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.factory.wordflowdailymindpuzzles.data.WordSearchResult>> observeWordSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWordSearchResultsForDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.factory.wordflowdailymindpuzzles.data.WordSearchResult>> $completion) {
        return null;
    }
}