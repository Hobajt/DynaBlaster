package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.objects.graphics.Animation;

public class Player extends Creature {

    private BombPool bombs;

    public Player(Point pos, Animation anim) {
        super(pos.x, pos.y, anim);
        bombs= new BombPool();
    }

    @Override
    public void move(Point moveVector) {
        addPosition(new Point(moveVector.x * speed, moveVector.y * speed));

        super.move(moveVector);
        getScreen().setViewPos(getPosition());
    }
}
