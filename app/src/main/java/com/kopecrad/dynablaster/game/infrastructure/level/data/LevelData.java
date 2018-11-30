package com.kopecrad.dynablaster.game.infrastructure.level.data;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.EnemyData;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.level.WinConditions;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.Block;
import com.kopecrad.dynablaster.game.objects.collidable.Wall;
import com.kopecrad.dynablaster.game.objects.collidable.creature.CreatureFactory;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Player;
import com.kopecrad.dynablaster.game.objects.tile.TileFactory;
import com.kopecrad.dynablaster.game.objects.tile.TileType;
import com.kopecrad.dynablaster.game.objects.tile.TilesetType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Data describing initial state of the level.
 * Extracted from xml.
 */
public class LevelData {

    int id;
    String name;

    List<EnemyData> enemies;
    Point playerSpawn;
    WinConditions conditions;
    TilesetType tileset;
    Point size;
    int[] map;

    public LevelData(int id) {
        this.id= id;
    }

    /**
     * Generates map tiling from row strings.
     */
    void setupMap(List<String> rows) {
        size= getMapSize(rows);
        map= parseMap(rows);
    }

    private int[] parseMap(List<String> rows) {
        int[] res= new int[size.x * size.y];
        for(int i= 0; i < rows.size(); i++) {
            parseMapRow(rows.get(i), res, i);
        }
        return res;
    }

    private void parseMapRow(String row, int[] res, int rowIndex) {
        String[] str= row.split("");
        //Starting from 1 bcs split leaves first elements empty
        for(int j= 1; j < str.length; j++) {
            res[rowIndex* size.x + j-1]= Integer.parseInt(str[j]);
        }

        //set values for tiles that were added bcs row lengths are inconsistent
        for(int j= str.length-1; j < size.x; j++) {
            res[rowIndex* size.x + j]= -1;
        }
    }

    /**
     * Calculates map size.
     * Map always converts to rectangular shape.
     */
    private Point getMapSize(List<String> rows) {
        int xSize= 0;
        for(String s : rows) {
            if(xSize < s.length())
                xSize= s.length();
        }
        return new Point(xSize, rows.size());
    }

    /**
     * Creates initial state for the level.
     * Generates level tiles and objects.
     */
    public LevelState generateState(PlayerProgress progress) {
        Log.d("kek", "--Generating level--");
        //create tiles from map array
        GameObject[] tiles= generateTiles();

        //create enemies
        List<Enemy> enemies= generateEnemies();

        //spawn player
        Log.d("kek","Player spawn pos: " + playerSpawn.toString());
        Player player= CreatureFactory.spawnPlayer(playerSpawn);
        player.setProgress(progress);

        return new LevelState(size, tiles, player, enemies, conditions);
    }

    private List<Enemy> generateEnemies() {
        List<Enemy> es= new ArrayList<>();

        for(EnemyData ed : enemies) {
            ed.generateEnemies(es);
        }

        return es;
    }

    private GameObject[] generateTiles() {
        GameObject[] tiles= new GameObject[size.x * size.y];
        for(int rowIdx= 0; rowIdx < size.y; rowIdx++) {
            for(int colIdx= 0; colIdx < size.x; colIdx++) {
                int pos = rowIdx * size.x + colIdx;
                tiles[pos]= TileFactory.CreateTile(colIdx, rowIdx, tileset, TileType.getByID(map[pos]));
            }
        }

        setPortalAndItem(tiles);
        updateWallSprites(tiles);
        return tiles;
    }

    private void setPortalAndItem(GameObject[] tiles) {
        boolean portalSet= false, itemSet= false;
        Random rd= new Random(System.nanoTime());

        while(!portalSet) {
            Point p= new Point(rd.nextInt(size.x), rd.nextInt(size.y));
            try {
                Block b = (Block) tiles[p.y * size.x + p.x];
                if(b.getPostDestructionEffect() != 0)
                    continue;
                b.setPostDestructionEffect(2);
                Log.d("kek", "Portal block set - " + p.toString());
                portalSet= true;
            } catch(ClassCastException e) { continue; }
        }
        while(!itemSet) {
            Point p= new Point(rd.nextInt(size.x), rd.nextInt(size.y));
            try {
                Block b = (Block) tiles[p.y * size.x + p.x];
                if(b.getPostDestructionEffect() != 0)
                    continue;
                b.setPostDestructionEffect(1);
                Log.d("kek", "Item block set - " + p.toString());
                itemSet= true;
            } catch(ClassCastException e) { continue; }
        }
    }

    private void updateWallSprites(GameObject[] map) {
        String tp =tileset.name().toLowerCase();
        for(int i= 0; i < size.y; i++) {
            try {
                ((Wall)map[i*size.x]).spriteUpdate(null, tp);
                ((Wall)map[i*size.x + size.y-1  ]).spriteUpdate(null, tp);
            } catch (ClassCastException e) {}
        }

        for(int i= 0; i < size.x; i++) {
            try {
                ((Wall) map[i]).spriteUpdate(null, tp);
                ((Wall) map[(size.y-1) * (size.x) + i]).spriteUpdate(null, tp);
            } catch (ClassCastException e) {}
        }
    }

    /*private void updateWallSprites(GameObject[] map) {
        for(int i= 0; i < size.y; i++) {
            for(int j= 0; j < size.x; j++) {
                GameObject g= map[i * size.x + j];
                if(g.isTraversable())
                    continue;

                try {
                    Wall w = (Wall) map[i * size.x + j];

                    List<GameObject> neighbors= new ArrayList<>();
                    for(int a= -1; a < 2; a++) {
                        if(i < 1) {
                            neighbors.add(null);
                            neighbors.add(null);
                            neighbors.add(null);
                            continue;
                        }

                        for(int b= -1; b < 2; b++) {
                            try {
                                neighbors.add(map[(i + a) * size.x + j + b]);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                neighbors.add(null);
                            }
                        }
                    }
                    w.spriteUpdate(neighbors, tileset.name().toLowerCase());

                } catch (ClassCastException e) {
                    continue;
                }
            }
        }
    }*/
}
