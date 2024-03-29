package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.MyPoint;
import com.kopecrad.dynablaster.game.MyRect;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.Updatable;
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
        MyRect obs= getBoundingRect();
        MyRect oth= other.getBoundingRect();

        //System.out.printf("OBS: (%1$s, %2$s, %3$s, %4$s)%n", obs.left, obs.right, obs.top, obs.bottom);
        //System.out.printf("OTH: (%1$s, %2$s, %3$s, %4$s)%n", oth.left, oth.right, oth.top, oth.bottom);

        if (obs.left < oth.right && obs.right > oth.left
                && obs.top < oth.bottom && obs.bottom > oth.top) {

            Point p= new MyPoint(obs.left - oth.right, obs.top - oth.bottom);
            int x= obs.right - oth.left;
            if(Math.abs(p.x) > Math.abs(x)) {
                //System.out.println("move x");
                p.x = x;
            }
            x= obs.bottom - oth.top;
            if(Math.abs(p.y) > Math.abs(x)) {
                //System.out.println("move y");
                p.y = x;
            }

            //System.out.printf("vec: %1$s, %2$s%n", p.x, p.y);
            //System.out.println("collision");
            return p;
        }
        //System.out.println("no collision");
        return null;
    }

    /**
     * AABB Collision check - no fixing, only true/false value.
     */
    public boolean isColliding(Collidable other) {
        MyRect obs= getBoundingRect();
        MyRect oth= other.getBoundingRect();

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
                collisionRes |= peerCollision(c.getRank(), c.getID());
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

    protected void fireIDInit(int id) {
        lastFireID= id;
    }

    protected boolean isFireUnique(int fireID) {
        if(lastFireID != fireID) {
            Log.d("kek", "Fire unique : " + fireID + " != " + lastFireID);
            lastFireID= fireID;
            return true;
        }
        return false;
    }

    protected int getID() {
        return -1;
    }
}
