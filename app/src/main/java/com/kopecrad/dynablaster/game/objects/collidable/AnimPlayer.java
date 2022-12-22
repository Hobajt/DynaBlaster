package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.kopecrad.dynablaster.game.MyRect;
import com.kopecrad.dynablaster.game.objects.Updatable;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;
import com.kopecrad.dynablaster.game.objects.graphics.GuestAnimation;

public class AnimPlayer implements Updatable {

    private GuestAnimation anim;

    private MyRect screenRect;

    public AnimPlayer(Animation anim, MyRect screenRect) {
        this.anim= new GuestAnimation(anim);
        this.screenRect= screenRect;
    }

    @Override
    public boolean update() {
        anim.update();
        return anim.isFinished();
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(anim.getFrame(), null, screenRect.Rekt(), null);
    }
}
