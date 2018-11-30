package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

import java.util.List;

/**
 * Collidable object that can move around the map.
 */
public abstract class Creature extends Collidable {

    public static final int SPEED_BASE = 12;

    protected int speed= SPEED_BASE;

    private Point moveVector;

    public Creature(int x, int y, ObjectGraphics graphics) {
        super(x, y, graphics);
        moveVector= new Point(0,0);
    }

    public Creature(int x, int y, String graphics) {
        super(x, y, graphics);
        moveVector= new Point(0,0);
    }

    public void move(Point moveVector) {
        int sp = (int)(speed * LevelState.getDeltaTime());
        addPosition(new Point(moveVector.x * sp, moveVector.y * sp));
        this.moveVector= moveVector;
    }

    /**
     * Called when collision is detected.
     * Reverts move done in last update.
     */
    public void revertMove(Point colTilePos) {
        //setPosition(getScreen().reverseMoveVector(colTilePos, moveVector, getPosition()));
        addPosition(new Point(-moveVector.x * speed, - moveVector.y * speed));
    }

    /**
     * Detects collisions on specified tiles.
     * If there are any, executes reverse move.
     */
    public void fixObstacleCols(List<GameObject> colTiles) {
        Point resPoint;
        for(GameObject go : colTiles) {
            if(go.isTraversable())
                continue;

            try {
                Collidable c = (Collidable) go;
                resPoint= c.detectAndRepairCollision(this);
                if(resPoint != null) {
                    addPosition(new Point(resPoint.x * Math.abs(moveVector.x), resPoint.y * Math.abs(moveVector.y)));
                    return;
                }
            } catch (ClassCastException e) {}
        }
    }

    /**
     * Returns map index of the closest map tile.
     */
    public Point getClosestTile() {
        return getScreen().getClosestIndex(getPosition());
    }

    @Override
    protected void setupBoundingRect() {
        boundingRect= getScreen().getCreatureRect(getPosition());
    }

    protected Point getMoveVector() {
        return moveVector;
    }

    @Override
    protected Rect getScreenRect() {
        return getScreen().getScreenRectCreature(boundingRect);
    }
}
