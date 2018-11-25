package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Point;
import android.util.Log;

/**
 * Data describing the level.
 * Extracted from xml.
 */
public class LevelData {

    private int id;
    private String name;

    private WinConditions conditions;
    private String tilesetPostfix;
    private Point size;
    private int[] map;

    private LevelData(int id) {
        this.id = id;
        this.name = name;

        Log.d("kek", "Data: " + id + ", " + name);
    }

    public static LevelData LoadLevel(int id) {
        //TODO:
    }
}
