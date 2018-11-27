package com.kopecrad.dynablaster.game.objects.collidable.creature;

import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

public class Enemy extends Creature {

    public Enemy(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.ENEMY;
    }

    @Override
    protected boolean peerCollision(CollidableRank other) {
        return other == CollidableRank.FIRE;
    }
}
