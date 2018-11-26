package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.InputHandler;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains live data related to level.
 * Contains all tiles, objects, etc.
 */
public class LevelState implements Renderable {

    private Point size;

    private GameObject[] map;
    private List<Collidable> objects;

    private List<Enemy> enemies;
    private Player player;

    private Point viewPos;
    private InputHandler playerInput;

    int cnt= 0;

    public LevelState(Point size, GameObject[] map, Player player) {
        this.size= size;
        this.map = map;
        this.player= player;

        objects= new ArrayList<>();
        objects.add(player);

        viewPos= new Point(6,6);
        playerInput= new InputHandler();
    }

    @Override
    public void renderUpdate(Canvas canvas) {
        handlePlayerInput();
        //handleEnemies();

        checkCollisions();

        //RENDER
        canvas.drawRGB(0, 0, 0);

        //render tiles
        for(GameObject t : map) {
            t.render(canvas);
        }

        //render collidables
        for(Collidable c : objects) {
            c.render(canvas);
        }
    }

    public InputHandler getInput() {
        return playerInput;
    }

    public GameObject getTileAt(Point colTile) {
        return map[colTile.y * size.x + colTile.x];
    }

    private void handlePlayerInput() {
        if(playerInput.bombSignal()) {
            //TODO: drop bomb via player->BombPool
            return;
        }

        if (playerInput.isMoving()) {
            player.move(playerInput.getMoveDir());
        }
    }

    private void checkCollisions() {
        //detect & fix obstacle collisions
        player.fixObstacleCols(getTargetTiles(player.getClosestTile(), player.getMoveVector()));
        /*for(Enemy e : enemies) {
            e.fixObstacleCols(getTargetTiles(e.getClosestTile(), e.getMoveVector()));
        }*/
    }

    /**
     * Fetches three possible collision tiles.
     */
    public List<GameObject> getTargetTiles(Point closestTile, Point moveDir) {
        List<GameObject> tiles= new ArrayList<>();
        boolean moveInY= moveDir.y != 0;
        if(!moveInY && moveDir.x == 0)
            return tiles;

        Point tgLine= new Point(closestTile.x + moveDir.x, closestTile.y + moveDir.y);
        if(tgLine.y < 0)
            tgLine.y= 0;
        if(tgLine.x < 0)
            tgLine.x= 0;


        Log.d("kek", tgLine.toString() + " ; " + size.x + " : " + moveInY);
        for(int i= -1; i < 2; i++) {
            if(moveInY)
                tiles.add(map[tgLine.y * size.x + tgLine.x + i]);
            else
                tiles.add(map[(tgLine.y + i) * size.x + tgLine.x]);
        }

        return tiles;
    }
}
