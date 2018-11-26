package com.kopecrad.dynablaster.game.objects.obstacle;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.Collidable;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;
import com.kopecrad.dynablaster.game.objects.tile.Tile;

/**
 * Tile that is untraversable (at least most of the time).
 */
public abstract class Obstacle extends Tile {

    private Point realPos;

    public Obstacle(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
        realPos= new Point(x *ScreenSettings.TILE_SIZE, y * ScreenSettings.TILE_SIZE);
    }

    public boolean isColliding(Collidable other) {
        //Log.d("kek", "Obstacle: ("+ getMapPos().x +", "+ getMapPos().y +")");
        //Log.d("kek", "Other: ("+ other.getMapPos().x +", "+ other.getMapPos().y +")");

        if(other.isMoveY()) {
            int oTilePos= Math.abs(realPos.y - other.getMapPos().y * ScreenSettings.TILE_SIZE);
            int oReal= Math.abs(realPos.y - other.getRealPos().y);
            return (oTilePos - oReal > 0);
        }
        else {
            int oTilePos = Math.abs(realPos.x - other.getMapPos().x * ScreenSettings.TILE_SIZE);
            int oReal = Math.abs(realPos.x - other.getRealPos().x);
            return (oTilePos - oReal > 0);
        }
    }
}
