package com.kopecrad.dynablaster.game.objects.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

/**
 * Map tile object.
 */
public class Tile extends GameObject {

    public Tile(int x, int y, ObjectGraphics texture) {
        super(x,y, texture);
    }
}
