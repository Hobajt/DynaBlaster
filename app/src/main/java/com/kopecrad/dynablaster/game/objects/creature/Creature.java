package com.kopecrad.dynablaster.game.objects.creature;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.Collidable;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;
import com.kopecrad.dynablaster.game.objects.tile.Tile;

/**
 * Collidable object that can move around the map.
 */
public abstract class Creature extends Collidable {

    protected int speed= 5;

    public Creature(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
    }

    @Override
    public void render(Canvas canvas, Point viewPos) {
        canvas.drawBitmap(getFrame(), null, getScreen().getCreatureRect(realPos, viewPos), null);
    }
}
