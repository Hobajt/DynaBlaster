package com.kopecrad.dynablaster.game.objects.tile;

import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.ObjectFactory;
import com.kopecrad.dynablaster.game.objects.collidable.Block;
import com.kopecrad.dynablaster.game.objects.collidable.Wall;

/**
 * Responsible for correct tile creation.
 */
public class TileFactory extends ObjectFactory {

    public static GameObject CreateTile(int xPos, int yPos, TilesetType tileset, TileType tile) {
        switch (tile) {
            case BLOCK:
                return new Block(xPos, yPos, tile.getIdentifier(tileset));
            case WALL:
                return new Wall(xPos, yPos, tile.getIdentifier(tileset));
            default:
                return new GameObject(xPos, yPos, tile.getIdentifier(tileset));
        }
    }
}
