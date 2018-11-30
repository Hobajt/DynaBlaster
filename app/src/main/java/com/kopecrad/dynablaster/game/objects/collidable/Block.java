package com.kopecrad.dynablaster.game.objects.collidable;

import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Obstacle;

/**
 * Obstacle that can be traversed only when certain conditions are met.
 * Example conditions: player has ghost.
 */
public class Block extends Obstacle {

    private static LevelState level;

    private int postDestructionEffect;

    public Block(int x, int y, String graphics) {
        super(x, y, graphics);
        this.postDestructionEffect= 0;
    }

    @Override
    protected boolean peerCollision(CollidableRank other, int fireID) {
        if(other != CollidableRank.FIRE)
            return false;

        Log.d("kek", "Block-fire collision; ID=" + fireID);

        if(isFireUnique(fireID)) {
            postDestruction(fireID);
            return true;
        }
        return false;
    }

    private void postDestruction(int fireID) {
        if(postDestructionEffect == 0)
            return;

        if(postDestructionEffect != 2) {
            Log.d("kek", "Spawning item");
            level.spawnNew(new Item(getMapPos(), ItemType.random(), fireID));
            return;
        }

        Log.d("kek", "Spawning portal");
        level.spawnNew(new Portal(getMapPos(), fireID));
    }

    public static void setLevelRef(LevelState lvl) {
        level= lvl;
    }

    public void setPostDestructionEffect(int postDestructionEffect) {
        this.postDestructionEffect = postDestructionEffect;
    }

    public int getPostDestructionEffect() {
        return postDestructionEffect;
    }
}
