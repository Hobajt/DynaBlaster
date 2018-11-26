package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

import java.util.List;

/**
 * Collidable object that can move around the map.
 */
public abstract class Creature extends Collidable {

    protected int speed= 5;

    private Point moveVector;

    public Creature(int x, int y, ObjectGraphics graphics) {
        super(x, y, graphics);
        moveVector= new Point(0,0);
    }

    public Creature(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    public void move(Point moveVector) {
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
        for(GameObject go : colTiles) {
            if(go.isTraversable())
                continue;

            try {
                Collidable c = (Collidable) go;
                if (c.isColliding(this)) {
                    //Log.d("kek", "HONEY DETECTED BBapper");

                    revertMove(c.getPosition());
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

    public Point getMoveVector() {
        return moveVector;
    }
}
