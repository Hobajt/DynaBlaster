package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.content.SharedPreferences;
import android.util.Log;

import com.kopecrad.dynablaster.game.objects.collidable.ItemType;

/**
 * Tracks players upgrades throughout the game.
 */
public class Buffs {

    private static final int SPEED_INCREMENT = 25;

    private static final int START_BOMBCAP= 3;
    private static final int START_FIRERADIUS = 1;
    private static final int START_SPEED = 4 * SPEED_INCREMENT;

    private static final String KEY_FIRERADIUS = "player.buffs.fireradius";
    private static final String KEY_BOMBCAP = "player.buffs.bombcap";
    private static final String KEY_GHOST = "player.buffs.ghost";
    private static final String KEY_SPEED = "player.buffs.speed";

    private int bombCap;
    private int fireRadius;
    private int speed;

    private boolean ghost;

    /**
     * For state reset.
     */
    public Buffs() {
        bombCap= START_BOMBCAP;
        fireRadius= START_FIRERADIUS;
        speed= START_SPEED;
        ghost= false;
    }

    /**
     * For state restoration.
     */
    public Buffs(SharedPreferences prefs) {
        bombCap= prefs.getInt(KEY_BOMBCAP, START_BOMBCAP);
        fireRadius= prefs.getInt(KEY_FIRERADIUS, START_FIRERADIUS);
        speed= prefs.getInt(KEY_SPEED, START_SPEED);

        ghost= prefs.getBoolean(KEY_GHOST, false);
    }

    /**
     * For use in player object (so that player doesn't modify saved data.
     */
    public Buffs(Buffs original) {
        this.bombCap= original.bombCap;
        this.fireRadius= original.fireRadius;
        this.speed= original.speed;

        this.ghost= original.ghost;
    }

    /**
     * Used in playerProgress during state saving.
     */
    public void saveState(SharedPreferences.Editor pEdit) {
        pEdit.putInt(KEY_BOMBCAP, bombCap);
        pEdit.putInt(KEY_FIRERADIUS, fireRadius);
        pEdit.putInt(KEY_SPEED, speed);

        pEdit.putBoolean(KEY_GHOST, ghost);
    }

    public int getBombCap() {
        return bombCap;
    }

    public int getFireRadius() {
        return fireRadius;
    }

    public boolean isGhost() {
        return ghost;
    }

    public int getSpeed() {
        return speed;
    }

    public void updateFireRadius() {
        fireRadius++;
    }

    public void updateBombCount() {
        bombCap++;
    }

    public void updateSpeed() {
        speed += SPEED_INCREMENT;
    }
}
