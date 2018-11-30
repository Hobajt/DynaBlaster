package com.kopecrad.dynablaster.game.infrastructure.level.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.level.EnemyDescription;

import java.util.HashMap;
import java.util.Map;

public class EnemyDescLoader extends SQLiteOpenHelper {

    private static Map<Integer, EnemyDescription> cache;

    public static final String DB_NAME = "GameDB.db";

    private static final String TABLE_NAME= "enemy";
    private static final String COL_ID = "id";
    private static final String COL_GRAPHICS = "graphics";
    private static final String COL_HEALTH = "health";
    private static final String COL_SPEED = "speed";

    public EnemyDescLoader(Context context) {
        super(context, DB_NAME, null, 2);
        Log.d("logDB", "DB constructor");
        if(cache == null)
            cache= new HashMap<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("logDB", "creating DB, inserting rows");
        db.execSQL("CREATE TABLE "+ TABLE_NAME +" (id INTEGER PRIMARY KEY, graphics TEXT, health INTEGER, speed INTEGER)");
        insertEnemy(db, new EnemyDescription(0, "enemy_0_anim", 50, 1));
        insertEnemy(db, new EnemyDescription(1, "enemy_1_anim", 75, 1));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private boolean insertEnemy(SQLiteDatabase db, EnemyDescription enemy) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", enemy.getId());
        contentValues.put("graphics", enemy.getGraphics());
        contentValues.put("health", enemy.getHealth());
        contentValues.put("speed", enemy.getSpeed());

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public EnemyDescription getEnemy(int id) {
        if(!cache.containsKey(id)) {
            EnemyDescription d= readEnemy(id);
            if(d != null)
                cache.put(d.getId(), d);
            return d;
        }

        return cache.get(id);
    }

    private EnemyDescription readEnemy(int id) {
        String selection = COL_ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs= db.query(
                TABLE_NAME,
                null,
                selection, selectionArgs,
                null, null,
                COL_ID + " DESC"
        );

        Log.d("logDB", "Cursor selection count: " + crs.getCount());
        if(crs.getCount() < 1)
            return null;

        crs.moveToNext();
        EnemyDescription ed=  new EnemyDescription(
            crs.getInt(crs.getColumnIndexOrThrow(COL_ID)),
            crs.getString(crs.getColumnIndexOrThrow(COL_GRAPHICS)),
            crs.getInt(crs.getColumnIndexOrThrow(COL_SPEED)),
            crs.getInt(crs.getColumnIndexOrThrow(COL_HEALTH))
        );

        crs.close();
        return ed;
    }
}
