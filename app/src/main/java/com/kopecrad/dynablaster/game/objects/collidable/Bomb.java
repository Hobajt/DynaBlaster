package com.kopecrad.dynablaster.game.objects.collidable;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.sound.SoundType;
import com.kopecrad.dynablaster.game.objects.Updatable;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Buffs;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Creature;
import com.kopecrad.dynablaster.game.objects.collidable.creature.Player;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Block implements Updatable {

    private static int fireCounter= 5;

    private static final String BOMB_GRAPHICS = "bomb_anim";
    private static final int BOMB_TIME_CONST= 3000;
    private static final int FIRE_TIME_CONST = 500;

    private boolean active;
    private long boomTime;
    private Point idxPos;

    private static Buffs playerBuffs;

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

//    public Bomb(Point position) {
//        super(position.x, position.y, BOMB_GRAPHICS);
//        active= true;
//        setPosition(getScreen().calcPosition(position.x, position.y));
//        idxPos= getScreen().getClosestIndex(getPosition());
//        boomTime= System.currentTimeMillis() + BOMB_TIME_CONST;
//    }

    public void activate(Point position) {
        active= true;
        setPosition(getScreen().calcPosition(position.x, position.y));
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
        getScene().getSounds().playSound(SoundType.EXPLOSION);

        int fireID = fireCounter++;

        List<Fire> f= new ArrayList<>();

        int radius= playerBuffs.getFireRadius();
        long expire= System.currentTimeMillis() + FIRE_TIME_CONST;

        for(int i= idxPos.x - radius; i <= idxPos.x + radius; i++) {
            f.add(new Fire(i, idxPos.y, expire, fireID));
        }
        for(int i= idxPos.y - radius; i <= idxPos.y + radius; i++) {
            if(i == idxPos.y)
                continue;

            f.add(new Fire(idxPos.x, i, expire, fireID));
        }

        state.registerUpdatables(f);
    }

    @Override
    public boolean fixPeerCols(List<Collidable> objects) {
        return false;
    }

    public static void setBuffsRef(Buffs b) {
        playerBuffs= b;
    }

    @Override
    public Point detectAndRepairCollision(Collidable other) {
        if(other.getRank() == CollidableRank.PLAYER || other.getRank() == CollidableRank.ENEMY) {
            Creature p = (Creature) other;
            Point mv= p.getMoveVector();
            Point pos= p.getMapPos();
            pos= new Point(pos.x + mv.x, pos.y + mv.y);

            Point myPos= getMapPos();
            if(myPos.x == pos.x && myPos.y == pos.y)
                return super.detectAndRepairCollision(other);
            return null;
        }

        return super.detectAndRepairCollision(other);
    }
}
