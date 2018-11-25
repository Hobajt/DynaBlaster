package com.kopecrad.dynablaster.game.objects.tile;

/**
 * For easier tile identifier reconstruction
 */
public enum TileType {
    NONE (-1),
    GROUND (0),
    WALL (1),
    BLOCK (2);

    private final int id;

    private TileType(int id) {
        this.id= id;
    }

    /**
     * Fetches tile type from tile code (in LevelData).
     */
    public static TileType getByID(int id) {
        for(TileType t : TileType.values()) {
            if(id == t.id)
                return t;
        }
        return TileType.NONE;
    }

    /**
     * Generates tile identifier.
     */
    public String getIdentifier(TilesetType tileset) {
        String s= this.name().toLowerCase();

        if(this == NONE)
            return s;

        return tileset.name().toLowerCase() + "_" + s;
    }
}
