package com.kopecrad.dynablaster.game.objects.creature;

import android.graphics.Bitmap;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

public class Enemy extends Creature {

    public Enemy(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
    }

    @Override
    public void checkCollision(LevelState state) {
        //TODO
    }


}
