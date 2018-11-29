package com.kopecrad.dynablaster.game.objects.tile;

import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.ObjectFactory;
import com.kopecrad.dynablaster.game.objects.collidable.Block;
import com.kopecrad.dynablaster.game.objects.collidable.Wall;

/**
 * Responsible for correct tile creation.
 */
public class TileFactory extends ObjectFactory {

    private static TilesetType tilesetCache;

    public static GameObject CreateTile(int xPos, int yPos) {
        return CreateTile(xPos, yPos, tilesetCache, TileType.GROUND);
    }

    public static GameObject CreateTile(int xPos, int yPos, TilesetType tileset, TileType tile) {
        tilesetCache= tileset;
        switch (tile) {
            case BLOCK:
                return new Block(xPos, yPos, tile.getIdentifier(tileset));
            case WALL:
                return new Wall(xPos, yPos, tile.getIdentifier(tileset));
            default:
                String s= tile.getIdentifier(tileset);
                if(s.equals("none"))
                    return new Wall(xPos, yPos, "none");
                else
                    return new GameObject(xPos, yPos, tile.getIdentifier(tileset));
        }
    }
}
