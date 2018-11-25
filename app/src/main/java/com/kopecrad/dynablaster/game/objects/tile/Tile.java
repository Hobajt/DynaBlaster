package com.kopecrad.dynablaster.game.objects.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.GameObject;

/**
 * Map tile object.
 */
public class Tile extends GameObject {

    private Bitmap texture;

    public Tile(int x, int y, Bitmap texture) {
        this(new Point(x,y), texture);
    }

    public Tile(Point pos, Bitmap texture) {
        super(pos);
        this.texture= texture;
    }

    @Override
    public void render(Canvas canvas, Point viewPos) {
        canvas.drawBitmap(texture, null, getScreen().getTileRect(getMapPos(), viewPos), null);
    }
}
