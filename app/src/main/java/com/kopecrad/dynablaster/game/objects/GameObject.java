package com.kopecrad.dynablaster.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

/**
 * Any object in the game, that can be visible.
 */
public abstract class GameObject {

    private static ScreenSettings screen;

    private ObjectGraphics texture;
    private Point mapPos;

    public GameObject(int x, int y, ObjectGraphics texture) {
        this.mapPos = new Point(x, y);
        this.texture= texture;
    }

    /**
     * Render call for this object.
     */
    public void render(Canvas canvas, Point viewPos) {
        canvas.drawBitmap(texture.getFrame(), null, getScreen().getTileRect(getMapPos(), viewPos), null);
    }

    public Point getMapPos() {
        return mapPos;
    }

    public void setPosition(Point p) {
        mapPos= p;
    }

    public static void setScreenSettings(ScreenSettings stg) {
        screen= stg;
    }

    protected ScreenSettings getScreen() {
        return screen;
    }

    protected Bitmap getFrame() {
        return texture.getFrame();
    }
}
