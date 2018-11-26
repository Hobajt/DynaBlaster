package com.kopecrad.dynablaster.game.infrastructure;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/**
 * Contains values related to screen dimensions.
 * Is used to properly position textures on screen.
 */
public class ScreenSettings {

//    public static final int TILES_PER_SMALLER_DIM = 12;

    public static final int TILE_SIZE = 64;
    public static final int TILE_SIZE_HALF = TILE_SIZE / 2;
//    private Point tileCount;

    private Point sizeHalf;

    public ScreenSettings() {
        InputHandler.setScreenRef(this);
    }

    /**
     * Generates rect position for given tile.
     */
    public Rect getTileRect(Point tilePos, Point viewPos) {
        Point pos= new Point(tilePos.x - viewPos.x, tilePos.y - viewPos.y);
        return new Rect(
                pos.y * TILE_SIZE + sizeHalf.x, pos.x * TILE_SIZE + sizeHalf.y,
                (pos.y+1) * TILE_SIZE + sizeHalf.x, (pos.x+1) * TILE_SIZE + sizeHalf.y
        );
    }

    public void updateSize(int w, int h) {
        sizeHalf= new Point(w/2, h/2);
        //boolean hSmaller= w > h;
        /*TILE_SIZE= (hSmaller ? h : w) / TILES_PER_SMALLER_DIM;
        tileCount= hSmaller
                ? new Point(w / TILE_SIZE, TILES_PER_SMALLER_DIM)
                : new Point(TILES_PER_SMALLER_DIM, h / TILE_SIZE);*/
    }

    public Point getScreenCenter() {
        return sizeHalf;
    }

    public Rect getCreatureRect(Point realPos, Point viewPos) {
        Point mPos= new Point(realPos.x - viewPos.x * TILE_SIZE, realPos.y - viewPos.y * TILE_SIZE);
        return new Rect(
                mPos.y + sizeHalf.x,
                mPos.x + sizeHalf.y,
                (mPos.y+TILE_SIZE) + sizeHalf.x,
                (mPos.x+TILE_SIZE) + sizeHalf.y
        );
    }
}
