package com.kopecrad.dynablaster.game.infrastructure;

import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.level.EnemyDescription;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;

import java.util.List;

/**
 * Info about single enemy type loaded from XML.
 */
public class EnemyData {

    private int count;
    private EnemyDescription description;

    public EnemyData(int count, int ID) {
        this.count= count;
        description= EnemyDescription.getByID(ID);
    }

    /**
     * Generates instances of the enemy specified in this object.
     */
    public void generateEnemies(List<Enemy> enemyList) {
        for(int i= 0; i < count; i++) {
            enemyList.add(new Enemy(description));
        }
    }
}
