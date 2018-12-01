package com.kopecrad.dynablaster.game.infrastructure.score;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.DBTableAccess;
import com.kopecrad.dynablaster.game.infrastructure.GameDB;

import java.util.List;

public class ScoreTableAccess extends DBTableAccess {

    private static final String TABLE_NAME = "score";

    private static final String COL_ID = "id";
    private static final String COL_PLAYER = "player";
    private static final String COL_SCORE = "score";
    private static final String COL_LEVELS = "levels";
    private static final String COL_DATE = "date";

    public ScoreTableAccess(GameDB gameDB) {
        super(gameDB);
    }

    @Override
    public void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY, "+
                COL_PLAYER + " TEXT, "+
                COL_SCORE +" INTEGER, "+
                COL_LEVELS +" INTEGER, "+
                COL_DATE +" TEXT)");
    }

    @Override
    public void upgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        create(db);
    }

    public boolean addEntry(Score score) {
        SQLiteDatabase db= gameDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLAYER, score.getPlayer());
        contentValues.put(COL_SCORE, score.getScore());
        contentValues.put(COL_DATE, score.getDate());
        contentValues.put(COL_LEVELS, score.getLevels());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public void removeAllEntries() {
        SQLiteDatabase db= gameDB.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public boolean loadAllScores(List<Score> cache) {
        SQLiteDatabase db= gameDB.getReadableDatabase();

        Cursor crs= db.query(
                TABLE_NAME, null,
                null, null,
                null, null,
                COL_ID + " DESC"
        );

        Log.d("logDB", "Score::Cursor selection count: " + crs.getCount());
        if(crs.getCount() < 1)
            return false;

        int counter= 0;
        try {
            while (crs.moveToNext()) {
                int id = crs.getInt(crs.getColumnIndexOrThrow(COL_ID));
                String player = crs.getString(crs.getColumnIndexOrThrow(COL_PLAYER));
                int score = crs.getInt(crs.getColumnIndexOrThrow(COL_SCORE));
                int levels = crs.getInt(crs.getColumnIndexOrThrow(COL_LEVELS));
                String date = crs.getString(crs.getColumnIndexOrThrow(COL_DATE));

                cache.add(new Score(player, score, date, levels));
                counter++;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        crs.close();

        Log.d("logDB", "Score::Loaded " + counter + " entries.");
        return true;
    }
}
