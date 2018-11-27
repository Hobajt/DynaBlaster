package com.kopecrad.dynablaster.game.objects.collidable;

import android.util.Log;

import com.kopecrad.dynablaster.game.objects.collidable.creature.Obstacle;

/**
 * Obstacle that can be traversed only when certain conditions are met.
 * Example conditions: player has ghost.
 */
public class Block extends Obstacle {

    public Block(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    @Override
    protected boolean peerCollision(CollidableRank other) {
        Log.d("kek", "Block:: Colliding with " + other.name());
        if(other == CollidableRank.FIRE) {
            //TODO: chance to generate item drop
            return true;
        }
        return false;
    }
}
