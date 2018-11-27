package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;
import android.widget.TextView;

import com.kopecrad.dynablaster.game.infrastructure.GameState;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.objects.collidable.Bomb;
import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;

public class Player extends Creature {

    private BombPool bombs;

    private int health;
    private Buffs buffs;

    public Player(Point pos, Animation anim) {
        super(pos.x, pos.y, anim);
        bombs= new BombPool();
        health= 0;
    }

    @Override
    public void move(Point moveVector) {
        addPosition(new Point(moveVector.x * speed, moveVector.y * speed));

        super.move(moveVector);
        getScreen().setViewPos(getPosition());
    }

    public void setProgress(PlayerProgress progress) {
        this.health= progress.getHealth();
        this.buffs= new Buffs(progress.getBuffs());

        bombs.setup(buffs.getBombCap());
    }

    public Bomb dropBomb() {
        return bombs.dropBomb(getPosition());
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.PLAYER;
    }

    @Override
    protected boolean peerCollision(CollidableRank other) {
        switch (other) {
            case FIRE:
            case ENEMY:
                return takeDamage();
            case ITEM:
                //TODO
                break;
            case PORTAL:
                //TODO
                break;
        }
        return false;
    }

    private boolean takeDamage() {
        if(--health < 1) {
            getScene().setLifeCount(health);
            getScene().levelFinished(GameState.PLAYER_DIED);
            return true;
        }
        getScene().setLifeCount(health);
        return false;
    }
}
