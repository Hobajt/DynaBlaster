package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Canvas;
import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.InputHandler;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.Updatable;
import com.kopecrad.dynablaster.game.objects.collidable.Block;
import com.kopecrad.dynablaster.game.objects.collidable.Bomb;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.collidable.Fire;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Player;
import com.kopecrad.dynablaster.game.objects.tile.TileFactory;

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
    private List<Block> blocks;

    private List<Enemy> enemies;
    private Player player;

    private Point viewPos;
    private InputHandler playerInput;

    int cnt= 0;

    private List<Updatable> upds;

    public LevelState(Point size, GameObject[] map, Player player, List<Enemy> enemies) {
        this.size= size;
        this.map = map;
        this.player= player;
        this.enemies= enemies;
        upds= new ArrayList<>();
        blocks= getBlockInMap();

        objects= new ArrayList<>();
        objects.add(player);

        viewPos= new Point(6,6);
        playerInput= new InputHandler();

        Bomb.setStateRef(this);
    }

    private List<Block> getBlockInMap() {
        List<Block> b= new ArrayList<>();
        Block bl;

        for(GameObject g : map) {
            try {
                bl= (Block) g;
                b.add(bl);
            } catch (ClassCastException e) {}
        }

        return b;
    }

    @Override
    public void renderUpdate(Canvas canvas) {
        playerUpdate();
        //enemyUpdate();

        //bombs and fire updates
        updateUpdatable();

        obstacleCollisions();
        objectCollisions();

        render(canvas);
    }

    private void render(Canvas canvas) {
        canvas.drawRGB(0, 0, 0);

        for(GameObject t : map)
            t.render(canvas);

        for(Collidable c : objects)
            c.render(canvas);
    }

    private void updateUpdatable() {
        for(int i= upds.size()-1; i >= 0; i--) {
            if(upds.get(i).update()) {
                Updatable u= upds.get(i);
                objects.remove(u);
                upds.remove(i);
            }
        }
    }

    public InputHandler getInput() {
        return playerInput;
    }

    public GameObject getTileAt(Point colTile) {
        return map[colTile.y * size.x + colTile.x];
    }

    private void playerUpdate() {
        if(playerInput.bombSignal()) {
            Bomb b= player.dropBomb();
            if(b != null) {
                objects.add(b);
                upds.add(b);
            }
        }

        if (playerInput.isMoving()) {
            player.move(playerInput.getMoveDir());
        }
    }

    private void obstacleCollisions() {
        //detect & fix obstacle collisions
        player.fixObstacleCols(getTargetTiles(player.getClosestTile()));
        for(Enemy e : enemies) {
            e.fixObstacleCols(getTargetTiles(e.getClosestTile()));
        }
    }

    private void objectCollisions() {
        List<Collidable> rem= new ArrayList<>();
        //iterate all collidable agains each other
        for(Collidable c : objects) {
            if(c.fixPeerCols(objects)) {
                rem.add(c);
            }
        }

        List<Block> remB= new ArrayList<>();
        //iterate all blocks bcs of fire destruction
        for(Block b : blocks) {
            if(b.fixPeerCols(objects))
                remB.add(b);
        }

        //remove marked objects
        for(Collidable c : rem)
            objects.remove(c);

        for(Block b : remB) {
            removeMapBlock(b);
        }
    }

    private void removeMapBlock(Block b) {
        Point pos= b.getMapPos();
        map[pos.y * size.x + pos.x]= TileFactory.CreateTile(pos.x, pos.y);
        blocks.remove(b);
    }

    /**
     * Fetches three possible collision tiles.
     */
    public List<GameObject> getTargetTiles(Point closestTile) {
        List<GameObject> tiles= new ArrayList<>();

        //Log.d("kek", closestTile.toString());
        for(int i= -1; i < 2; i++) {
            for(int j= -1; j < 2; j++) {
                try {
                    tiles.add(map[(closestTile.y + i) * size.x + closestTile.x + j]);
                } catch (IndexOutOfBoundsException e) {}
            }
        }

        return tiles;
    }

    public void registerUpdatables(List<Fire> f) {
        for(Fire fire : f) {
            objects.add(fire);
            upds.add(fire);
        }
    }
}
