package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;

public class Portal extends Collidable {

    public static final String GRAPHICS = "portal";

    public Portal(Point mapPos, int fireID) {
        super(mapPos.x, mapPos.y, GRAPHICS);
        setLastFireID(fireID);
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.PORTAL;
    }

    @Override
    protected boolean peerCollision(CollidableRank other, int fireID) {
        return false;
    }
}
