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

    private Point viewPos;
    private Point screenCenter;

    public ScreenSettings() {
        viewPos= new Point(0,0);
        InputHandler.setScreenRef(this);
    }

    public void updateSize(int w, int h) {
        screenCenter = new Point(w/2, h/2);
    }

    public Point getScreenCenter() {
        return screenCenter;
    }

    /**
     * Calculates GameObject rectangle for rendering.
     */
    public Rect getObjectRect(Point realPos) {
        return new Rect(realPos.x, realPos.y, realPos.x+TILE_SIZE, realPos.y+TILE_SIZE);
    }

    /**
     * Translates tile index positions into real pixel position.
     */
    public Point calcPosition(int x, int y) {
        return new Point(x * TILE_SIZE, y * TILE_SIZE);
    }

    public void setViewPos(Point pos) {
        viewPos= new Point(pos.x - screenCenter.x, pos.y - screenCenter.y);
    }

    /**
     * Applies view vector to the bounding rect.
     */
    public Rect getScreenRect(Rect boundingRect) {
        return new Rect(
            boundingRect.left - viewPos.x,
            boundingRect.top - viewPos.y,
            boundingRect.right - viewPos.x,
            boundingRect.bottom - viewPos.y
        );
    }

    public Point getClosestIndex(Point position) {
        boolean addX= position.x % TILE_SIZE > TILE_SIZE_HALF * 1.2;
        boolean addY= position.y % TILE_SIZE > TILE_SIZE_HALF * 1.2;

        return new Point(
                position.x / TILE_SIZE + (addX ? 1 : 0),
                position.y / TILE_SIZE + (addY ? 1 : 0)
        );
    }

    public Point reverseMoveVector(Point colTilePos, Point moveVector, Point myPos) {
        if(moveVector.x == 0) {
            return new Point(myPos.x, colTilePos.y - moveVector.y * TILE_SIZE);
        }
        else {
            return new Point(colTilePos.x - moveVector.x * TILE_SIZE, myPos.y);
        }
    }
}
