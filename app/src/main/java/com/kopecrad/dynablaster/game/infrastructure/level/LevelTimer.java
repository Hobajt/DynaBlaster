package com.kopecrad.dynablaster.game.infrastructure.level;

import android.util.Log;

import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;

import java.util.Locale;

public class LevelTimer {

    private WinConditions conds;

    private int minutes;
    private float secs;

    private long timeExpire;

    public LevelTimer(WinConditions conds) {
        this.conds = conds;

        minutes= conds.getTimeLimit() / 60;
        secs= conds.getTimeLimit() % 60;
    }

    public void startTimer() {
        timeExpire= System.currentTimeMillis() + (conds.getTimeLimit() * 1000);
        Log.d("kek", "TimerStart: " + this.toString());
    }

    public void update(float deltaTime) {
        secs -= deltaTime;
        if(secs <= 0) {
            minutes--;
            secs= 60.f;
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= timeExpire;
    }

    @Override
    public String toString() {
        return Integer.toString(minutes) + ":" + String.format(Locale.ENGLISH, "%d", (int)secs);
    }

    public int getTimeLeft() {
        return (int)((timeExpire - System.currentTimeMillis())* 0.001);
    }
}
