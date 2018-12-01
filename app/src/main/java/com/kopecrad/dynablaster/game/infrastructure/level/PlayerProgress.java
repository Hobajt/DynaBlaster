package com.kopecrad.dynablaster.game.infrastructure.level;

import android.content.SharedPreferences;
import android.util.Log;

import com.kopecrad.dynablaster.game.objects.collidable.Bomb;
import com.kopecrad.dynablaster.game.objects.collidable.ItemType;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Buffs;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains player's progress in the current run.
 * Only contains data required between levels (not actual level data).
 */
public class PlayerProgress {

    private static final int START_HEALTH = 4;
    private static final int START_LEVEL = 0;

    private static final String KEY_LEVEL= "player.level";
    private static final String KEY_HEALTH= "player.health";
    private static final String KEY_SCORE= "player.score";
    private static final String KEY_LEVELS_CLEARED= "player.levelsCleared";

    private SharedPreferences prefs;

    private int nextLevel;
    private int health;
    private int score;
    private int levelsCleared;
    private Buffs buffs;

    /**
     * Creates instance with already loaded progress values.
     */
    public PlayerProgress(SharedPreferences prefs) {
        this.prefs= prefs;
        restoreState();
    }

    /**
     * Used in resetProgress - empty progress.
     */
    private PlayerProgress() {
        buffs= new Buffs();
        nextLevel= START_LEVEL;
        health= START_HEALTH;
        score= 0;
        levelsCleared= 0;
    }

    /**
     * Checks if there is any progress saved in preferences.
     * @return Returns true when no progress is saved.
     */
    public static boolean noProgress(SharedPreferences prefs) {
        return prefs.getInt(KEY_LEVEL, START_LEVEL) == START_LEVEL;
    }

    /**
     * Saves current progress state into the preferences.
     */
    public void saveState() {
        SharedPreferences.Editor pEdit= prefs.edit();

        pEdit.putInt(KEY_LEVEL, nextLevel);
        pEdit.putInt(KEY_HEALTH, health);
        pEdit.putInt(KEY_SCORE, score);
        pEdit.putInt(KEY_LEVELS_CLEARED, levelsCleared);
        buffs.saveState(pEdit);

        pEdit.apply();
    }

    /**
     * Loads progress state from preferences.
     */
    public void restoreState() {
        nextLevel = prefs.getInt(KEY_LEVEL, START_LEVEL);
        health = prefs.getInt(KEY_HEALTH, START_HEALTH);
        score = prefs.getInt(KEY_SCORE, 0);
        levelsCleared= prefs.getInt(KEY_LEVELS_CLEARED, 0);
        buffs= new Buffs(prefs);
        Bomb.setProgressRef(this);
    }

    /**
     * Erases current game run.
     */
    public static void resetProgress(SharedPreferences prefs) {
        PlayerProgress prg= new PlayerProgress();
        prg.prefs= prefs;
        prg.saveState();
    }

    public void resetProgress() {
        Log.d("kek", "Player progress reset.");
        PlayerProgress prg= new PlayerProgress();
        prg.prefs= this.prefs;
        prg.saveState();
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public Buffs getBuffs() {
        return buffs;
    }

    public int getLevelsCleared() {
        return levelsCleared;
    }

    public void update(int health, int score, Buffs buffs, int timeLeft) {
        this.health= health;
        this.score= score + timeLeft * 10;
        this.levelsCleared++;
        Log.d("kek", "levelCleared: " + levelsCleared);
        this.nextLevel++;

        this.buffs= new Buffs(buffs);
        Log.d("kek", "Player progress saved.");

        saveState();
    }
}
