package com.kopecrad.dynablaster.game.objects.collidable.creature;

import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;

public abstract class Obstacle extends Collidable {

    public Obstacle(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.OBSTACLE;
    }
}
