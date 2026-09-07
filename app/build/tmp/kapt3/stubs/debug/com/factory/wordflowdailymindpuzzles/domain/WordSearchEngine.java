package com.factory.wordflowdailymindpuzzles.domain;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00042\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bJ(\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u0011J\u001e\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0006R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/domain/WordSearchEngine;", "", "()V", "directions", "", "Lkotlin/Pair;", "", "cellsAlongLine", "Lcom/factory/wordflowdailymindpuzzles/domain/GridCell;", "start", "end", "generate", "Lcom/factory/wordflowdailymindpuzzles/domain/WordSearchPuzzle;", "words", "", "size", "seed", "", "snapToLine", "current", "app_debug"})
public final class WordSearchEngine {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> directions = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.factory.wordflowdailymindpuzzles.domain.WordSearchEngine INSTANCE = null;
    
    private WordSearchEngine() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.factory.wordflowdailymindpuzzles.domain.WordSearchPuzzle generate(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> words, int size, long seed) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.factory.wordflowdailymindpuzzles.domain.GridCell snapToLine(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.domain.GridCell start, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.domain.GridCell current, int size) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.factory.wordflowdailymindpuzzles.domain.GridCell> cellsAlongLine(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.domain.GridCell start, @org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.domain.GridCell end) {
        return null;
    }
}