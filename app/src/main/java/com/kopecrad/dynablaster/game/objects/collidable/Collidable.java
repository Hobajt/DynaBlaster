package com.kopecrad.dynablaster.game.objects.collidable;

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
    public boolean isColliding(Collidable other) {
        Rect obs= getBoundingRect();
        Rect oth= other.getBoundingRect();

        return (obs.left < oth.right && obs.right > oth.left &&
                obs.top < oth.bottom && obs.bottom > oth.top);
    }
}
