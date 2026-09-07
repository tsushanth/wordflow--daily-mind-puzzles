package com.factory.wordflowdailymindpuzzles.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public final class DailyPuzzleDao_Impl implements DailyPuzzleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyPuzzleResult> __insertionAdapterOfDailyPuzzleResult;

  public DailyPuzzleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyPuzzleResult = new EntityInsertionAdapter<DailyPuzzleResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_puzzle_results` (`date`,`word`,`solved`,`attempts`,`hintsUsed`,`timeTakenMs`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyPuzzleResult entity) {
        if (entity.getDate() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getDate());
        }
        if (entity.getWord() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getWord());
        }
        final int _tmp = entity.getSolved() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getAttempts());
        statement.bindLong(5, entity.getHintsUsed());
        statement.bindLong(6, entity.getTimeTakenMs());
      }
    };
  }

  @Override
  public Object upsert(final DailyPuzzleResult result,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyPuzzleResult.insert(result);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByDate(final String date,
      final Continuation<? super DailyPuzzleResult> $completion) {
    final String _sql = "SELECT * FROM daily_puzzle_results WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyPuzzleResult>() {
      @Override
      @Nullable
      public DailyPuzzleResult call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "solved");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfHintsUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "hintsUsed");
          final int _cursorIndexOfTimeTakenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTakenMs");
          final DailyPuzzleResult _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpWord;
            if (_cursor.isNull(_cursorIndexOfWord)) {
              _tmpWord = null;
            } else {
              _tmpWord = _cursor.getString(_cursorIndexOfWord);
            }
            final boolean _tmpSolved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSolved);
            _tmpSolved = _tmp != 0;
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final int _tmpHintsUsed;
            _tmpHintsUsed = _cursor.getInt(_cursorIndexOfHintsUsed);
            final long _tmpTimeTakenMs;
            _tmpTimeTakenMs = _cursor.getLong(_cursorIndexOfTimeTakenMs);
            _result = new DailyPuzzleResult(_tmpDate,_tmpWord,_tmpSolved,_tmpAttempts,_tmpHintsUsed,_tmpTimeTakenMs);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DailyPuzzleResult>> observeAll() {
    final String _sql = "SELECT * FROM daily_puzzle_results ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_puzzle_results"}, new Callable<List<DailyPuzzleResult>>() {
      @Override
      @NonNull
      public List<DailyPuzzleResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "solved");
          final int _cursorIndexOfAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "attempts");
          final int _cursorIndexOfHintsUsed = CursorUtil.getColumnIndexOrThrow(_cursor, "hintsUsed");
          final int _cursorIndexOfTimeTakenMs = CursorUtil.getColumnIndexOrThrow(_cursor, "timeTakenMs");
          final List<DailyPuzzleResult> _result = new ArrayList<DailyPuzzleResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyPuzzleResult _item;
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpWord;
            if (_cursor.isNull(_cursorIndexOfWord)) {
              _tmpWord = null;
            } else {
              _tmpWord = _cursor.getString(_cursorIndexOfWord);
            }
            final boolean _tmpSolved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSolved);
            _tmpSolved = _tmp != 0;
            final int _tmpAttempts;
            _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            final int _tmpHintsUsed;
            _tmpHintsUsed = _cursor.getInt(_cursorIndexOfHintsUsed);
            final long _tmpTimeTakenMs;
            _tmpTimeTakenMs = _cursor.getLong(_cursorIndexOfTimeTakenMs);
            _item = new DailyPuzzleResult(_tmpDate,_tmpWord,_tmpSolved,_tmpAttempts,_tmpHintsUsed,_tmpTimeTakenMs);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
