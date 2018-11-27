package com.kopecrad.dynablaster.game.infrastructure.level.data;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.level.WinConditions;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.collidable.creature.CreatureFactory;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Enemy;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Player;
import com.kopecrad.dynablaster.game.objects.tile.TileFactory;
import com.kopecrad.dynablaster.game.objects.tile.TileType;
import com.kopecrad.dynablaster.game.objects.tile.TilesetType;

import java.util.ArrayList;
import java.util.List;

/**
 * Data describing initial state of the level.
 * Extracted from xml.
 */
public class LevelData {

    int id;
    String name;

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
        //create tiles from map array
        GameObject[] tiles= generateTiles();

        //create enemies


        //spawn player
        Player player= CreatureFactory.spawnPlayer(playerSpawn);
        player.setProgress(progress);

        return new LevelState(size, tiles, player, new ArrayList<Enemy>());
    }

    private GameObject[] generateTiles() {
        GameObject[] tiles= new GameObject[size.x * size.y];
        for(int rowIdx= 0; rowIdx < size.y; rowIdx++) {
            for(int colIdx= 0; colIdx < size.x; colIdx++) {
                int pos = rowIdx * size.x + colIdx;
                tiles[pos]= TileFactory.CreateTile(colIdx, rowIdx, tileset, TileType.getByID(map[pos]));
            }
        }
        return tiles;
    }
}
