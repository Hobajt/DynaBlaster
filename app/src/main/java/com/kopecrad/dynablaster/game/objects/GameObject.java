package com.kopecrad.dynablaster.game.objects;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;

/**
 * Any object in the game, that can be visible.
 */
public abstract class GameObject {

    private static ScreenSettings screen;

    private Point mapPos;

    public GameObject(Point mapPos) {
        this.mapPos = mapPos;
    }

    public abstract void render(Canvas canvas, Point viewPos);

    public Point getMapPos() {
        return mapPos;
    }

    public static void setScreenSettings(ScreenSettings stg) {
        screen= stg;
    }

    protected ScreenSettings getScreen() {
        return screen;
    }
}
