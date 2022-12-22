package com.kopecrad.dynablaster.game.infrastructure;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.MyPoint;
import com.kopecrad.dynablaster.game.MyRect;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;

/**
 * Contains values related to screen dimensions.
 * Is used to properly position textures on screen.
 */
public class ScreenSettings {

    private static LevelState levelState;

//    public static final int TILES_PER_SMALLER_DIM = 12;

    public static int TILE_SIZE = 64;
    public static int CREATURE_REDUCTION = TILE_SIZE / 5;
    public static int PLAYER_ADDITION = 4*TILE_SIZE / 10;//(int)(TILE_SIZE * 0.4f);
    public static int TILE_SIZE_HALF = TILE_SIZE / 2;
//    private Point tileCount;

    private Point viewPos;
    private Point screenCenter;
    private int height;

    public ScreenSettings() {
        viewPos= new Point(0,0);
        InputHandler.setScreenRef(this);
    }

    public static void setStateRef(LevelState state) {
        levelState= state;
    }

    public void updateSize(int w, int h) {
        rescale(w, h);
    }

    public Point getScreenCenter() {
        return screenCenter;
    }

    /**
     * Calculates GameObject rectangle for rendering.
     */
    public MyRect getObjectRect(Point realPos) {
        return new MyRect(realPos.x, realPos.y, realPos.x+TILE_SIZE, realPos.y+TILE_SIZE);
    }

    /**
     * Translates tile index positions into real pixel position.
     */
    public Point calcPosition(int x, int y) {
        return new MyPoint(x * TILE_SIZE, y * TILE_SIZE);
    }

    public void setViewPos(Point pos) {
        viewPos= new Point(pos.x - screenCenter.x, pos.y - screenCenter.y);
    }

    /**
     * Applies view vector to the bounding rect.
     */
    public MyRect getScreenRect(MyRect boundingRect) {
        return new MyRect(
            boundingRect.left - viewPos.x,
            boundingRect.top - viewPos.y,
            boundingRect.right - viewPos.x,
            boundingRect.bottom - viewPos.y
        );
    }

    public Point getClosestIndex(Point position) {
        boolean addX= position.x % TILE_SIZE > TILE_SIZE_HALF;
        boolean addY= position.y % TILE_SIZE > TILE_SIZE_HALF;

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

    public MyRect getCreatureRect(Point realPos) {
        return new MyRect(
                realPos.x + CREATURE_REDUCTION,
                realPos.y + CREATURE_REDUCTION,
                realPos.x+TILE_SIZE  - CREATURE_REDUCTION,
                realPos.y+TILE_SIZE - CREATURE_REDUCTION
        );
    }

    public MyRect getScreenRectCreature(MyRect boundingRect) {
        return new MyRect(
                boundingRect.left - CREATURE_REDUCTION - viewPos.x,
                boundingRect.top - CREATURE_REDUCTION - viewPos.y,
                boundingRect.right + CREATURE_REDUCTION - viewPos.x,
                boundingRect.bottom + CREATURE_REDUCTION - viewPos.y
        );
    }

    public MyRect getScreenRectPlayer(MyRect boundingRect) {
        return new MyRect(
                boundingRect.left - (PLAYER_ADDITION/2) - viewPos.x,
                boundingRect.top - PLAYER_ADDITION - viewPos.y,
                boundingRect.right + (PLAYER_ADDITION/2) - viewPos.x,
                boundingRect.bottom - viewPos.y
        );
    }

    private void rescale(int w, int h) {
        height= h;
        int oldTileSize= TILE_SIZE;
        TILE_SIZE= w > h ? h/12 : w/12;
        screenCenter = new Point(w/2, h/2);
        updateMeasures();

        if(levelState != null)
            levelState.rescale(oldTileSize);
    }

    private void updateMeasures() {
        CREATURE_REDUCTION = TILE_SIZE / 5;
        PLAYER_ADDITION = 4*TILE_SIZE / 10;//(int)(TILE_SIZE * 0.4f);
        TILE_SIZE_HALF = TILE_SIZE / 2;
    }

    public int getHeight() {
        return height;
    }
}
