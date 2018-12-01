package com.kopecrad.dynablaster.game.infrastructure.level;

import com.kopecrad.dynablaster.game.infrastructure.level.data.EnemyTableAccess;

/**
 * Enemy description class, loaded from DB.
 */
public class EnemyDescription {

    private static EnemyTableAccess enemyTable;

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

    public static void setEnemyTableRef(EnemyTableAccess edl) {
        enemyTable= edl;
    }
}
