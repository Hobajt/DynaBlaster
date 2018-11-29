package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.level.EnemyDescription;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

import java.util.List;

public class Enemy extends Creature {

    private static int enemyCounter= 0;

    private EnemyDescription desc;
    private int enemyID;

    public Enemy(EnemyDescription description) {
        super(0, 0, description.getGraphics());
        desc= description;
        enemyID= enemyCounter++;
    }

    public Enemy(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.ENEMY;
    }

    protected boolean peerCollision(CollidableRank other, int fireID) {
        if(other != CollidableRank.FIRE)
            return false;

        Log.d("kek", "Enemy collides with " + other.name() + ": " + fireID);
        if(isFireUnique(fireID)) {
            setLastFireID(fireID);
            //possible spot to implement multi-health enemies
            return true;
        }
        return false;
    }

    @Override
    protected int getID() {
        return enemyID;
    }

    //TODO: movement & ai logic


}
