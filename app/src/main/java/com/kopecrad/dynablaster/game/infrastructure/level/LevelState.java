package com.kopecrad.dynablaster.game.infrastructure.level;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.tile.Tile;

/**
 * Contains live data related to level.
 * Contains all tiles, objects, etc.
 */
public class LevelState implements Renderable {

    private Point size;
    private Tile[] map;
    //private List<Collidable> objects;

    private Point viewPos;

    public LevelState(Point size, Tile[] map) {
        this.size= size;
        this.map = map;

        viewPos= new Point(0,0);
    }

    @Override
    public void renderUpdate(Canvas canvas) {
        canvas.drawRGB(0, 0, 0);

        for(Tile t : map) {
            t.render(canvas, viewPos);
        }


    }
}
