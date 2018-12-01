package com.kopecrad.dynablaster.game.objects.graphics;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.Map;

public class PlayerAnimation implements ObjectGraphics {

    private Map<Integer, Animation> animMap;

    private Point active;

    public PlayerAnimation(Map<Integer, Animation> animMap) {
        this.animMap= animMap;
    }

    public void updateActive(Point active) {
        this.active= active;
    }

    @Override
    public Bitmap getFrame() {
        try {
            return animMap.get(2+active.x + 10*(active.y+2)).getFrame();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Animation getGhost() {
        return null;
    }
}
