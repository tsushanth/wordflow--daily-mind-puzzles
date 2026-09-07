package com.factory.wordflowdailymindpuzzles.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\rH\'\u00a8\u0006\u000e"}, d2 = {"Lcom/factory/wordflowdailymindpuzzles/data/WordSearchDao;", "", "getForDate", "", "Lcom/factory/wordflowdailymindpuzzles/data/WordSearchResult;", "date", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "result", "(Lcom/factory/wordflowdailymindpuzzles/data/WordSearchResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "app_release"})
@androidx.room.Dao()
public abstract interface WordSearchDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.factory.wordflowdailymindpuzzles.data.WordSearchResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM word_search_results ORDER BY id DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.factory.wordflowdailymindpuzzles.data.WordSearchResult>> observeAll();
    
    @androidx.room.Query(value = "SELECT * FROM word_search_results WHERE date = :date ORDER BY id DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getForDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.factory.wordflowdailymindpuzzles.data.WordSearchResult>> $completion);
}