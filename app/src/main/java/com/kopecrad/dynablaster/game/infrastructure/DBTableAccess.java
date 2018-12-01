package com.kopecrad.dynablaster.game.infrastructure;

import android.database.sqlite.SQLiteDatabase;

public abstract class DBTableAccess {

    protected GameDB gameDB;

    public DBTableAccess(GameDB gameDB) {
        this.gameDB = gameDB;
    }

    public abstract void create(SQLiteDatabase db);

    public abstract void upgrade(SQLiteDatabase db, int oldV, int newV);
}
