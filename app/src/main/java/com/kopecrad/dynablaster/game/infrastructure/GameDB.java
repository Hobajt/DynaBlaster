package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kopecrad.dynablaster.game.infrastructure.level.data.EnemyTableAccess;
import com.kopecrad.dynablaster.game.infrastructure.score.ScoreTableAccess;

public class GameDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "GameDB.db";
    public static final int DB_VERSION = 5;

    private EnemyTableAccess tableEnemy;
    private ScoreTableAccess tableScore;

    public GameDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        getTableEnemy().create(db);
        getTableScore().create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        getTableEnemy().upgrade(db, oldV, newV);
        getTableScore().upgrade(db, oldV, newV);
    }

    public EnemyTableAccess getTableEnemy() {
        if(tableEnemy == null)
            tableEnemy= new EnemyTableAccess(this);
        return tableEnemy;
    }

    public ScoreTableAccess getTableScore() {
        if(tableScore == null)
            tableScore= new ScoreTableAccess(this);
        return tableScore;
    }
}
