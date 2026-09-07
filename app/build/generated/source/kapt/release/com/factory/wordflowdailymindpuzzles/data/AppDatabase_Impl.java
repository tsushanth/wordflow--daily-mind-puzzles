package com.factory.wordflowdailymindpuzzles.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile DailyPuzzleDao _dailyPuzzleDao;

  private volatile WordSearchDao _wordSearchDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_puzzle_results` (`date` TEXT NOT NULL, `word` TEXT NOT NULL, `solved` INTEGER NOT NULL, `attempts` INTEGER NOT NULL, `hintsUsed` INTEGER NOT NULL, `timeTakenMs` INTEGER NOT NULL, PRIMARY KEY(`date`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `word_search_results` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` TEXT NOT NULL, `category` TEXT NOT NULL, `wordsFound` INTEGER NOT NULL, `totalWords` INTEGER NOT NULL, `timeTakenMs` INTEGER NOT NULL, `completed` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'de681e40e964befbb8dac1147a63fb61')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `daily_puzzle_results`");
        db.execSQL("DROP TABLE IF EXISTS `word_search_results`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsDailyPuzzleResults = new HashMap<String, TableInfo.Column>(6);
        _columnsDailyPuzzleResults.put("date", new TableInfo.Column("date", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyPuzzleResults.put("word", new TableInfo.Column("word", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyPuzzleResults.put("solved", new TableInfo.Column("solved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyPuzzleResults.put("attempts", new TableInfo.Column("attempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyPuzzleResults.put("hintsUsed", new TableInfo.Column("hintsUsed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyPuzzleResults.put("timeTakenMs", new TableInfo.Column("timeTakenMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyPuzzleResults = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyPuzzleResults = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyPuzzleResults = new TableInfo("daily_puzzle_results", _columnsDailyPuzzleResults, _foreignKeysDailyPuzzleResults, _indicesDailyPuzzleResults);
        final TableInfo _existingDailyPuzzleResults = TableInfo.read(db, "daily_puzzle_results");
        if (!_infoDailyPuzzleResults.equals(_existingDailyPuzzleResults)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_puzzle_results(com.factory.wordflowdailymindpuzzles.data.DailyPuzzleResult).\n"
                  + " Expected:\n" + _infoDailyPuzzleResults + "\n"
                  + " Found:\n" + _existingDailyPuzzleResults);
        }
        final HashMap<String, TableInfo.Column> _columnsWordSearchResults = new HashMap<String, TableInfo.Column>(7);
        _columnsWordSearchResults.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordSearchResults.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordSearchResults.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordSearchResults.put("wordsFound", new TableInfo.Column("wordsFound", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordSearchResults.put("totalWords", new TableInfo.Column("totalWords", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordSearchResults.put("timeTakenMs", new TableInfo.Column("timeTakenMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordSearchResults.put("completed", new TableInfo.Column("completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWordSearchResults = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWordSearchResults = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWordSearchResults = new TableInfo("word_search_results", _columnsWordSearchResults, _foreignKeysWordSearchResults, _indicesWordSearchResults);
        final TableInfo _existingWordSearchResults = TableInfo.read(db, "word_search_results");
        if (!_infoWordSearchResults.equals(_existingWordSearchResults)) {
          return new RoomOpenHelper.ValidationResult(false, "word_search_results(com.factory.wordflowdailymindpuzzles.data.WordSearchResult).\n"
                  + " Expected:\n" + _infoWordSearchResults + "\n"
                  + " Found:\n" + _existingWordSearchResults);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "de681e40e964befbb8dac1147a63fb61", "d50ee53483994dd4c40cedce696311d4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "daily_puzzle_results","word_search_results");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `daily_puzzle_results`");
      _db.execSQL("DELETE FROM `word_search_results`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DailyPuzzleDao.class, DailyPuzzleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WordSearchDao.class, WordSearchDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public DailyPuzzleDao dailyPuzzleDao() {
    if (_dailyPuzzleDao != null) {
      return _dailyPuzzleDao;
    } else {
      synchronized(this) {
        if(_dailyPuzzleDao == null) {
          _dailyPuzzleDao = new DailyPuzzleDao_Impl(this);
        }
        return _dailyPuzzleDao;
      }
    }
  }

  @Override
  public WordSearchDao wordSearchDao() {
    if (_wordSearchDao != null) {
      return _wordSearchDao;
    } else {
      synchronized(this) {
        if(_wordSearchDao == null) {
          _wordSearchDao = new WordSearchDao_Impl(this);
        }
        return _wordSearchDao;
      }
    }
  }
}
