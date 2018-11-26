package com.kopecrad.dynablaster.game.objects.graphics;

import android.graphics.Bitmap;

public class Animation implements ObjectGraphics {

    private Bitmap[] frames;
    private int frame;

    public Animation(Bitmap[] frames) {
        this.frames= frames;
        frame= 0;
    }

    @Override
    public Bitmap getFrame() {
        return frames[frame];
    }

    public void nextFrame() {
        if(++frame >= frames.length-1)
            frame= 0;
    }
}
