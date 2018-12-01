package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.GameState;
import com.kopecrad.dynablaster.game.infrastructure.InputHandler;
import com.kopecrad.dynablaster.game.infrastructure.Scene;
import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.Updatable;
import com.kopecrad.dynablaster.game.objects.collidable.AnimPlayer;
import com.kopecrad.dynablaster.game.objects.collidable.Block;
import com.kopecrad.dynablaster.game.objects.collidable.Bomb;
import com.kopecrad.dynablaster.game.objects.collidable.Collidable;
import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;
import com.kopecrad.dynablaster.game.objects.collidable.Fire;
import com.kopecrad.dynablaster.game.objects.collidable.Portal;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Player;
import com.kopecrad.dynablaster.game.objects.tile.TileFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains live data related to level.
 * Contains all tiles, objects, etc.
 */
public class LevelState implements Renderable {

    private static Scene scene;

    private Point size;

    private GameObject[] map;
    private List<Collidable> objects;
    private List<Block> blocks;

    private List<Enemy> enemies;
    private Player player;
    private InputHandler playerInput;

    private long lastTick;
    private static float deltaTime;

    private List<Updatable> upds;
    private List<Collidable> spawnQueue;
    private LevelTimer timer;

    private int enemiesLeft;
    private Portal portal = null;
    private List<Bomb> bombs;

    private List<AnimPlayer> anims;
    private boolean isOver;

    public LevelState(Point size, GameObject[] map, Player player, List<Enemy> enemies, WinConditions conds) {
        this.size= size;
        this.map = map;
        this.player= player;
        this.enemies= enemies;
        upds= new ArrayList<>();
        blocks= getBlockInMap();
        isOver= false;

        objects= new ArrayList<>();
        objects.add(player);
        objects.addAll(enemies);
        enemiesLeft= enemies.size();
        bombs= new ArrayList<>();

        anims= new ArrayList<>();
        playerInput= InputHandler.inst();

        setupEnemyPos();
        GameObject.setStateRef(this);
        ScreenSettings.setStateRef(this);
        Block.setLevelRef(this);

        spawnQueue= new ArrayList<>();
        timer= new LevelTimer(conds);
        timer.startTimer();

        scene.getGUI().update(player.getHealth(), player.getScore(), timer.toString());
    }

    public static void setSceneRef(Scene sc) {
        scene= sc;
    }

    /**
     * Scatters enemies randomly around the level.
     */
    private void setupEnemyPos() {
        Random r = new Random(System.nanoTime());
        boolean validPos;

        for(int i= 0; i < enemies.size(); i++) {
            int x, y;
            int loopBreakCounter= 0;
            do {
                x = r.nextInt(size.x);
                y = r.nextInt(size.y);

                validPos = map[y * size.x + x].isTraversable();
                if (validPos)
                    validPos = player.getMapDistance(new Point(x, y)) > 3;
                else
                    loopBreakCounter++;
            } while(!validPos && loopBreakCounter < 10);

            //fix when distance condition cannot be matched
            while(!validPos) {
                x = r.nextInt(size.x);
                y = r.nextInt(size.y);
                validPos = map[y * size.x + x].isTraversable();
            }

            Log.d("kek", "Enemy positioned at: " + x + ", " + y);
            enemies.get(i).setMapPosition(x, y);
        }
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
        timeUpdate();

        playerUpdate();
        enemyUpdate();

        //bombs and fire updates
        updateUpdatable();

        obstacleCollisions();
        objectCollisions();

        render(canvas);
        GameObject.ehm();
    }

    private void enemyUpdate() {
        for(Enemy e : enemies) {
            e.aiUpdate(map, player);
        }
    }

    private void timeUpdate() {
        long now= System.currentTimeMillis();
        deltaTime= ((now - lastTick)/1000.f);
        lastTick= now;

        if(deltaTime > 0.1f)
            deltaTime= 0.1f;

        timer.update(deltaTime);
        scene.getGUI().updateClock(timer.toString());

        if(timer.isExpired()) {
            scene.levelFinished(GameState.TIMES_UP);
        }
    }

    private void render(Canvas canvas) {
        canvas.drawRGB(0, 0, 0);

        for(GameObject t : map)
            t.render(canvas);

        for(Collidable c : objects)
            c.render(canvas);

        for(AnimPlayer a : anims) {
            a.render(canvas);
        }

        player.render(canvas);
    }

    private void updateUpdatable() {
        for(int i= upds.size()-1; i >= 0; i--) {
            if(upds.get(i).update()) {
                Updatable u= upds.get(i);
                objects.remove(u);
                anims.remove(upds.get(i));
                bombs.remove(upds.get(i));
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
                bombs.add(b);
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
        //iterate all collidables against each other
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
        for(Collidable c : rem) {
            Log.d("kek", "Removing " + c.getRank().name());
            if(c.getRank() == CollidableRank.ENEMY) {
                player.addScore(100);
                scene.getGUI().updateScore(Integer.toString(player.getScore()));
                Updatable ghost= ((Enemy)c).createGhost();
                if(ghost != null) {
                    Log.d("kek", "adding ghost");
                    upds.add(ghost);
                    anims.add((AnimPlayer)ghost);
                }
                enemies.remove(c);
                enemiesLeft--;
                Log.d("kek", "Enemy died, current count: " + enemiesLeft);
                if(portal != null)
                    portal.setState(enemiesLeft <= 0);
            }
            objects.remove(c);
        }

        for(Block b : remB) {
            Log.d("kek", "Removing " + b.getRank().name());
            removeMapBlock(b);
        }

        //add objects queued for spawning (can't do so during the for loops)
        if(spawnQueue.size() > 0) {
            for (Collidable c : spawnQueue) {
                if(c.getRank() == CollidableRank.PORTAL) {
                    portal= (Portal)c;
                    Log.d("kek","Spawning portal (enemyCount: " + enemiesLeft);
                    portal.setState(enemiesLeft <= 0);
                }
                Log.d("kek", "Spawning new object - " + c.getRank().name());
                objects.add(c);
            }
            spawnQueue.clear();
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

        closest:
        for(int i= -1; i < 2; i++) {
            for(int j= -1; j < 2; j++) {
                int yPos= (closestTile.y + i);
                int xPos= closestTile.x + j;
                try {
                    bombIter:
                    for(Bomb b : bombs) {
                        Point p= b.getMapPos();
                        if(p.x == xPos && p.y == yPos) {
                            tiles.add(b);
                            continue closest;
                        }
                    }

                    tiles.add(map[yPos * size.x + xPos]);
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

    public static float getDeltaTime() {
        return deltaTime;
    }

    public void rescale(int oldTileSize) {
        for(GameObject o : map) {
            o.rescale(oldTileSize);
        }

        for(Collidable c : objects) {
            c.rescale(oldTileSize);
        }
    }

    /**
     * Adds new collidable into the level (like an item or portal)
     */
    public void spawnNew(Collidable c) {
        spawnQueue.add(c);
    }

    public void updateHealth(int lives) {
        scene.getGUI().updateLives(Integer.toString(lives));
    }

    public void portalAttempt() {
        if(!timer.isExpired() && enemiesLeft <= 0 && portal != null && !isOver) {
            isOver= true;
            //portal.quitting();
            scene.levelFinished(GameState.LEVEL_COMPLETED);
        }
    }

    public void updateProgress(PlayerProgress prg) {
        prg.update(player.getHealth(), player.getScore(), player.getBuffs(), timer.getTimeLeft());
    }
}
