package com.kopecrad.dynablaster.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

/**
 * Object that has some response when it enters something.
 */
public abstract class Collidable extends GameObject {

    protected Point realPos;
    protected boolean moveY;

    public Collidable(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
        realPos= calcRealPos();
    }

    /**
     * Called when collision is detected.
     */
    public void revertMove() {}

    public Point getRealPos() {
        return realPos;
    }

    public boolean isMoveY() {
        return moveY;
    }

    private Point calcRealPos() {
        Point p= getMapPos();
        return new Point(p.x * ScreenSettings.TILE_SIZE, p.y * ScreenSettings.TILE_SIZE);
    }

    public abstract void checkCollision(LevelState state);
}
