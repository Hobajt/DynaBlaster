package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

import java.util.List;

public abstract class Collidable extends GameObject {

    private int lastFireID;

    public Collidable(int x, int y, ObjectGraphics graphics) {
        super(x, y, graphics);
        lastFireID= -1;
    }

    public Collidable(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    /**
     * AABB collision detection and response.
     * Detects whether provided collidable is interfering with this obstacle.
     * Returns counter move (or null when no collision happened).
     */
    public Point detectAndRepairCollision(Collidable other) {
        Rect obs= getBoundingRect();
        Rect oth= other.getBoundingRect();

        if (obs.left < oth.right && obs.right > oth.left &&
                obs.top < oth.bottom && obs.bottom > oth.top) {

            Point p= new Point(obs.left - oth.right, obs.top - oth.bottom);
            int x= obs.right - oth.left;
            if(Math.abs(p.x) > Math.abs(x))
                p.x= x;
            x= obs.bottom - oth.top;
            if(Math.abs(p.y) > Math.abs(x))
                p.y= x;
            return p;
        }
        return null;
    }

    /**
     * AABB Collision check - no fixing, only true/false value.
     */
    public boolean isColliding(Collidable other) {
        Rect obs= getBoundingRect();
        Rect oth= other.getBoundingRect();

        return (obs.left < oth.right && obs.right > oth.left &&
                obs.top < oth.bottom && obs.bottom > oth.top);
    }

    /**
     * Detects if this collidable collides with any other object.
     * If it does, peerCollision() is called on other colliding objects.
     * Returns true if object should get deleted.
     */
    public boolean fixPeerCols(List<Collidable> objects) {
        boolean collisionRes= false;
        for(Collidable c : objects) {
            if(c == this)
                continue;

            if(isColliding(c)) {
                collisionRes= peerCollision(c.getRank(), c.getID());
            }
        }

        return collisionRes;
    }

    public abstract CollidableRank getRank();

    /**
     * Triggered when object collides with other collidable.
     * Use for logic implementation - dealing damage, item picking, portal entering...
     * Returns true when this object is removed in the process.
     */
    protected abstract boolean peerCollision(CollidableRank other, int fireID);

    protected void setLastFireID(int lastFireID) {
        this.lastFireID = lastFireID;
    }

    protected boolean isFireUnique(int fireID) {
        return fireID != lastFireID;
    }

    protected int getID() {
        return -1;
    }
}
