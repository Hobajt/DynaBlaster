package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;
import android.util.Log;

import java.util.List;

public class Item extends Collidable {

    private ItemType type;

    public Item(Point mapPos, ItemType type, int fireID) {
        super(mapPos.x, mapPos.y, getItemGraphics(type));
        this.type= type;
        setLastFireID(fireID);
    }

    private static String getItemGraphics(ItemType type) {
        return "item_" + type.name().toLowerCase();
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.ITEM;
    }

    @Override
    protected boolean peerCollision(CollidableRank other, int fireID) {
        if(other != CollidableRank.FIRE) {
            return other == CollidableRank.PLAYER;
        }

        if(isFireUnique(fireID)) {
            setLastFireID(fireID);
            return true;
        }
        return false;
    }

    public ItemType getType() {
        return type;
    }

    @Override
    protected int getID() {
        return type.ordinal();
    }
}
