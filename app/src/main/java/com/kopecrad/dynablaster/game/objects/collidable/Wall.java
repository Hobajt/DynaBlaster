package com.kopecrad.dynablaster.game.objects.collidable;

import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Obstacle;

import java.util.List;

/**
 * Obstacle that can never be traversed.
 */
public class Wall extends Obstacle {

    public Wall(int x, int y, String graphics) {
        super(x, y, graphics);
    }

    @Override
    protected boolean peerCollision(CollidableRank other, int fireID) {
        return false;
    }

    public void spriteUpdate(List<GameObject> neighbors, String tilesetPrefix) {
        changeTexture(tilesetPrefix + "_wall_corner");
    }
}
