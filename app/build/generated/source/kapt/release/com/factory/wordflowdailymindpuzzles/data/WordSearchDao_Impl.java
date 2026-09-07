package com.factory.wordflowdailymindpuzzles.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WordSearchDao_Impl implements WordSearchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WordSearchResult> __insertionAdapterOfWordSearchResult;

  public WordSearchDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWordSearchResult = new EntityInsertionAdapter<WordSearchResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `word_search_results` (`id`,`date`,`category`,`wordsFound`,`totalWords`,`timeTakenMs`,`completed`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WordSearchResult entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDate() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDate());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategory());
        }
        statement.bindLong(4, entity.getWordsFound());
        statement.bindLong(5, entity.getTotalWords());
        statement.bindLong(6, entity.getTimeTakenMs());
        final int _tmp = entity.getCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
  }

  @Override
  public Object insert(final WordSearchResult result,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWordSearchResult.insert(result);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WordSearchResult>> observeAll() {
    final String _sql = "SELECT * FROM word_search_results ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"word_search_results"}, new Callable<List<WordSearchResult>>() {
      @Override
      @NonNull
      public List<WordSearchResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfWordsFound = CursorUtil.getColumnIndexOrThrow(_cursor, "wordsFound");
          final int _cursorIndexOfTotalWords = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWords");
          final int _cursorIndexOfTimeTakenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTakenMs");
          final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
          final List<WordSearchResult> _result = new ArrayList<WordSearchResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WordSearchResult _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final int _tmpWordsFound;
            _tmpWordsFound = _cursor.getInt(_cursorIndexOfWordsFound);
            final int _tmpTotalWords;
            _tmpTotalWords = _cursor.getInt(_cursorIndexOfTotalWords);
            final long _tmpTimeTakenMs;
            _tmpTimeTakenMs = _cursor.getLong(_cursorIndexOfTimeTakenMs);
            final boolean _tmpCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompleted);
            _tmpCompleted = _tmp != 0;
            _item = new WordSearchResult(_tmpId,_tmpDate,_tmpCategory,_tmpWordsFound,_tmpTotalWords,_tmpTimeTakenMs,_tmpCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getForDate(final String date,
      final Continuation<? super List<WordSearchResult>> $completion) {
    final String _sql = "SELECT * FROM word_search_results WHERE date = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<WordSearchResult>>() {
      @Override
      @NonNull
      public List<WordSearchResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfWordsFound = CursorUtil.getColumnIndexOrThrow(_cursor, "wordsFound");
          final int _cursorIndexOfTotalWords = CursorUtil.getColumnIndexOrThrow(_cursor, "totalWords");
          final int _cursorIndexOfTimeTakenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTakenMs");
          final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
          final List<WordSearchResult> _result = new ArrayList<WordSearchResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WordSearchResult _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final int _tmpWordsFound;
            _tmpWordsFound = _cursor.getInt(_cursorIndexOfWordsFound);
            final int _tmpTotalWords;
            _tmpTotalWords = _cursor.getInt(_cursorIndexOfTotalWords);
            final long _tmpTimeTakenMs;
            _tmpTimeTakenMs = _cursor.getLong(_cursorIndexOfTimeTakenMs);
            final boolean _tmpCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompleted);
            _tmpCompleted = _tmp != 0;
            _item = new WordSearchResult(_tmpId,_tmpDate,_tmpCategory,_tmpWordsFound,_tmpTotalWords,_tmpTimeTakenMs,_tmpCompleted);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
