package com.kopecrad.dynablaster.game.objects.collidable;

import com.kopecrad.dynablaster.game.objects.collidable.creature.Obstacle;

/**
 * Obstacle that can be traversed only when certain conditions are met.
 * Example conditions: player has ghost.
 */
public class Block extends Obstacle {

    public Block(int x, int y, String graphics) {
        super(x, y, graphics);
    }
}
