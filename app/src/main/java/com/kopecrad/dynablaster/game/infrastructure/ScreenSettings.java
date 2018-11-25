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

    private static final int tileSize = 64;
//    private Point tileCount;

    private Point sizeHalf;

    /**
     * Generates rect position for given tile.
     */
    public Rect getTileRect(Point tilePos, Point viewPos) {
        Point pos= new Point(tilePos.x - viewPos.x, tilePos.y - viewPos.y);
        return new Rect(
                pos.y * tileSize + sizeHalf.y, pos.x * tileSize + sizeHalf.x,
                (pos.y+1) * tileSize + sizeHalf.y, (pos.x+1) * tileSize + sizeHalf.x
        );
    }

    public void updateSize(int w, int h) {
        sizeHalf= new Point(h/2, w/2);
        //boolean hSmaller= w > h;
        /*tileSize= (hSmaller ? h : w) / TILES_PER_SMALLER_DIM;
        tileCount= hSmaller
                ? new Point(w / tileSize, TILES_PER_SMALLER_DIM)
                : new Point(TILES_PER_SMALLER_DIM, h / tileSize);*/
    }
}
