package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.objects.Updatable;

import java.util.List;

public class Fire extends Collidable implements Updatable {

    private static final String FIRE_GRAPHICS = "fire_anim";

    private long expireTimer;

    public Fire(int x, int y, long expireTimer) {
        super(x, y, FIRE_GRAPHICS);
        this.expireTimer= expireTimer;
    }

    @Override
    public boolean update() {
        return System.currentTimeMillis() >= expireTimer;
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.FIRE;
    }

    @Override
    protected boolean peerCollision(CollidableRank other) {
        return false;
    }
}
