package com.kopecrad.dynablaster.game.objects.graphics;

import android.graphics.Bitmap;

public class GuestAnimation extends Animation {

    private boolean finished;

    public GuestAnimation(Animation anim) {
        super(anim.getFrames(), anim.getSpeedDelay());
        finished=false;
    }

    @Override
    public void update() {
        if(!finished)
            super.update();
    }

    @Override
    protected void framesFlip() {
        finished= true;
    }

    public boolean isFinished() {
        return finished;
    }
}
