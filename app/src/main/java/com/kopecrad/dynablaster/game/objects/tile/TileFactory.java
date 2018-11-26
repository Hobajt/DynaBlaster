package com.kopecrad.dynablaster.game.objects.tile;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;
import com.kopecrad.dynablaster.game.objects.ObjectFactory;
import com.kopecrad.dynablaster.game.objects.obstacle.Block;
import com.kopecrad.dynablaster.game.objects.obstacle.Wall;

/**
 * Responsible for correct tile creation.
 */
public class TileFactory extends ObjectFactory {

    public static Tile CreateTile(int xPos, int yPos, TilesetType tileset, TileType tile) {
        switch (tile) {
            case BLOCK:
                return new Block(xPos, yPos, getRes().getTexture(tile.getIdentifier(tileset)));
            case WALL:
                return new Wall(xPos, yPos, getRes().getTexture(tile.getIdentifier(tileset)));
            default:
                return new Tile(xPos, yPos, getRes().getTexture(tile.getIdentifier(tileset)));
        }
    }
}
