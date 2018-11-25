package com.kopecrad.dynablaster.game.objects.tile;

import com.kopecrad.dynablaster.game.infrastructure.ImageResources;

/**
 * Responsible for correct tile creation.
 */
public class TileFactory {

    private static ImageResources res;

    public static Tile CreateTile(int xPos, int yPos, TilesetType tileset, TileType tile) {
        /*switch (tile) {
            case BLOCK:
                return new Block();
            case WALL:
                return new Wall();
            default:
                return new Tile();
        }*/
        return new Tile(xPos, yPos, res.getTexture(tile.getIdentifier(tileset)));
    }

    public static void setResourceRef(ImageResources r) {
        res= r;
    }
}
