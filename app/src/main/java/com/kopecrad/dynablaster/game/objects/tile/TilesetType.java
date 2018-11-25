package com.kopecrad.dynablaster.game.objects.tile;

/**
 * For easier drawable identifier reconstruction.
 */
public enum TilesetType {
    GREEN (0),
    SNOW (1);

    private final int id;

    TilesetType(int id) {
        this.id= id;
    }

    public static TilesetType GetByID(String tilesetPostfix) {
        try {
            int tID = Integer.parseInt(tilesetPostfix);
            for (TilesetType t : TilesetType.values()) {
                if (tID == t.id)
                    return t;
            }
        } catch (NumberFormatException e) {}

        return TilesetType.GREEN;
    }
}
