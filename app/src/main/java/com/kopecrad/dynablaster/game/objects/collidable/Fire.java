package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.objects.Updatable;

import java.util.List;

public class Fire extends Collidable implements Updatable {

    private static final String FIRE_GRAPHICS = "fire_anim";

    private long expireTimer;
    private int fireID;

    public Fire(int x, int y, long expireTimer, int fireID) {
        super(x, y, FIRE_GRAPHICS);
        this.expireTimer= expireTimer;
        this.fireID= fireID;
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
    protected boolean peerCollision(CollidableRank other, int fireID) {
        return false;
    }

    @Override
    protected int getID() {
        return fireID;
    }
}
