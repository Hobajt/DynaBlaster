package com.kopecrad.dynablaster.game.infrastructure.level;

import com.kopecrad.dynablaster.game.infrastructure.level.data.EnemyDescLoader;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

/**
 * Enemy description class, loaded from DB.
 */
public class EnemyDescription {

    private static EnemyDescLoader enemyTable;

    private int id;
    private String graphics;
    private int speed;

    private int health;

    public EnemyDescription(int id, String graphics, int speed, int health) {
        this.id = id;
        this.graphics = graphics;
        this.speed = speed;
        this.health = health;
    }

    public static EnemyDescription getByID(int id) {
        return enemyTable.getEnemy(id);
    }

    public String getGraphics() {
        return graphics;
    }

    public int getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public static void setEnemyTableRef(EnemyDescLoader edl) {
        enemyTable= edl;
    }
}
