package com.kopecrad.dynablaster.game.objects.collidable;

import com.kopecrad.dynablaster.game.objects.collidable.creature.Obstacle;

/**
 * Obstacle that can never be traversed.
 */
public class Wall extends Obstacle {

    public Wall(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    @Override
    protected boolean peerCollision(CollidableRank other) {
        return false;
    }
}
