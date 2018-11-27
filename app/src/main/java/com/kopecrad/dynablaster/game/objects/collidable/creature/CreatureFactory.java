package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.objects.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

public class CreatureFactory extends ObjectFactory {

    private static CreatureFactory instance;
    private static CreatureFactory inst() {
        if(instance == null)
            instance= new CreatureFactory();
        return instance;
    }

    public CreatureFactory() {
        player= null;
        enemies= new HashMap<>();
    }

    private Player player;
    private Map<String, Enemy> enemies;

    private Enemy spawnEnemyLoc(int x, int y, int enemyID) {
        //TODO:
        return null;
    }

    private Player spawnPlayerLoc(Point pos) {
        if(player == null)
            player= new Player(pos, getRes().getAnim("player_anim"));
        else
            player.setPosition(pos);
        return player;
    }

    public static Enemy spawnEnemy(int x, int y, int enemyID) {
        return inst().spawnEnemyLoc(x, y, enemyID);
    }

    public static Player spawnPlayer(Point pos) {
        return inst().spawnPlayerLoc(pos);
    }
}
