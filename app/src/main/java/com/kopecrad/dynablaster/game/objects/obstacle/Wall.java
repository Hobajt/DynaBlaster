package com.kopecrad.dynablaster.game.objects.obstacle;

import android.graphics.Bitmap;

import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;
import com.kopecrad.dynablaster.game.objects.obstacle.Obstacle;
import com.kopecrad.dynablaster.game.objects.tile.Tile;

public class Wall extends Obstacle {

    public Wall(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
    }
}
