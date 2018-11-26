package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.InputHandler;
import com.kopecrad.dynablaster.game.objects.Collidable;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.creature.Enemy;
import com.kopecrad.dynablaster.game.objects.creature.Player;
import com.kopecrad.dynablaster.game.objects.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains live data related to level.
 * Contains all tiles, objects, etc.
 */
public class LevelState implements Renderable {

    private Point size;

    private Tile[] map;
    private List<Collidable> objects;

    private List<Enemy> enemies;
    private Player player;

    private Point viewPos;
    private InputHandler playerInput;

    int cnt= 0;

    public LevelState(Point size, Tile[] map, Player player) {
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

        //GAME UPDATE
        if (playerInput.isMoving()) {
            //TODO: move player
            player.move(playerInput.getMoveDir());
        }

        for(Collidable c : objects) {
            c.checkCollision(this);
        }

        //RENDER
        canvas.drawRGB(0, 0, 0);

        //render tiles
        for(Tile t : map) {
            t.render(canvas, viewPos);
        }

        //render collidables
        for(Collidable c : objects) {
            c.render(canvas, viewPos);
        }


    }

    public InputHandler getInput() {
        return playerInput;
    }

    public Tile getTileAt(Point colTile) {
        return map[colTile.y * size.x + colTile.x];
    }
}
