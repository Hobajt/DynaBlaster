package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.objects.Updatable;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Block implements Updatable {

    private static PlayerProgress progress;
    private static LevelState state;

    private static final String BOMB_GRAPHICS = "bomb_anim";
    private static final int BOMB_TIME_CONST= 3000;
    private static final int FIRE_TIME_CONST = 500;

    private boolean active;
    private long boomTime;
    private Point idxPos;

    public Bomb(int x, int y, String graphics) {
        super(x, y, graphics);
        idxPos= getScreen().getClosestIndex(getPosition());
    }

    /**
     * BombPool constructor.
     */
    public Bomb() {
        super(0, 0, BOMB_GRAPHICS);
        active= false;
        boomTime= 0;
        idxPos= new Point(0,0);
    }

    public void activate(Point position) {
        active= true;
        setPosition(position);
        idxPos= getScreen().getClosestIndex(getPosition());
        boomTime= System.currentTimeMillis() + BOMB_TIME_CONST;
    }

    public void deactivate() {
        active= false;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean update() {
        if(!active)
            return false;

        if(System.currentTimeMillis() >= boomTime) {
            explode();
            deactivate();
            return true;
        }
        return false;
    }

    public void explode() {
        List<Fire> f= new ArrayList<>();

        int radius= progress.getBuffs().getFireRadius();
        long expire= System.currentTimeMillis() + FIRE_TIME_CONST;

        for(int i= idxPos.x - radius; i <= idxPos.x + radius; i++) {
            f.add(new Fire(i, idxPos.y, expire));
        }
        for(int i= idxPos.y - radius; i <= idxPos.y + radius; i++) {
            if(i == idxPos.y)
                continue;

            f.add(new Fire(idxPos.x, i, expire));
        }

        state.registerUpdatables(f);
    }

    public static void setProgressRef(PlayerProgress prg) {
        progress= prg;
    }

    public static void setStateRef(LevelState st) {
        state= st;
    }

    @Override
    protected boolean peerCollision(CollidableRank other) {
        return false;
    }
}
