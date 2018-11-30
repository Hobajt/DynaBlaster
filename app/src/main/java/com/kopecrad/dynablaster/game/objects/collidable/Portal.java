package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;

public class Portal extends Collidable {

    public static final String GRAPHICS = "portal";
    public static final String GRAPHICS_OPENED = "portal_anim";

    private boolean open;

    public Portal(Point mapPos, int fireID) {
        super(mapPos.x, mapPos.y, GRAPHICS);
        fireIDInit(fireID);
        open= false;
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.PORTAL;
    }

    @Override
    protected boolean peerCollision(CollidableRank other, int fireID) {
        return false;
    }

    public void setState(boolean open) {
        this.open= open;
        if(open) {
            changeTexture(GRAPHICS_OPENED);
        }
    }
}
