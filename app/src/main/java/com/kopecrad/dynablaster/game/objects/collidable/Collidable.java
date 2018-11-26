package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

public abstract class Collidable extends GameObject {

    public Collidable(int x, int y, ObjectGraphics graphics) {
        super(x, y, graphics);
    }

    public Collidable(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    /**
     * Detects whether provided collidable is interfering with this obstacle.
     */
    public Point isColliding(Collidable other) {
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
}
