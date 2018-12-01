package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.GameState;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.sound.SoundType;
import com.kopecrad.dynablaster.game.objects.collidable.Bomb;
import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;
import com.kopecrad.dynablaster.game.objects.collidable.ItemType;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;

public class Player extends Creature {

    private int lastEnemy;
    private long enemyContactTimestamp;

    private BombPool bombs;

    private int health;
    private Buffs buffs;
    private int score;

    public Player(Point pos, Animation anim) {
        super(pos.x, pos.y, anim);
        bombs= new BombPool();
        health= 0;
        lastEnemy= -1;
        enemyContactTimestamp= 0;
    }

    @Override
    public void move(Point moveVector) {
        super.move(moveVector);
        getScreen().setViewPos(getPosition());
    }

    public void setProgress(PlayerProgress progress) {
        this.health= progress.getHealth();
        this.buffs= new Buffs(progress.getBuffs());
        Bomb.setBuffsRef(buffs);
        score= progress.getScore();
        bombs.setup(buffs.getBombCap());
        updateSpeed(false);
        Log.d("kek", "Player speed: " + speed);
    }

    public Bomb dropBomb() {
        return bombs.dropBomb(getScreen().getClosestIndex(getPosition()));
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.PLAYER;
    }

    @Override
    protected boolean peerCollision(CollidableRank other, int fireID) {
        switch (other) {
            case PORTAL:
                state.portalAttempt();
                break;
            case ITEM:
                Log.d("kek", "Player: item picked up");
                itemPickup(fireID);
                //setup bonuses
                break;
            case ENEMY:
                if(isEnemyUnique(fireID))
                    return takeDamage();
                return false;
            case FIRE:
                if(isFireUnique(fireID)) {
                    return takeDamage();
                }
                return false;
        }
        return false;
    }

    public void itemPickup(int itemType) {
        ItemType type= ItemType.values()[itemType];

        switch (type) {
            case HEALTH:
                health++;
                state.updateHealth(health);
                break;
            case FIRE:
                buffs.updateFireRadius();
                break;
            case BOMB:
                buffs.updateBombCount();
                bombs.updateCount(buffs.getBombCap());
                break;
            case SPEED:
                updateSpeed(true);
                break;
        }
    }

    private void updateSpeed(boolean increment) {
        if(increment)
            buffs.updateSpeed();
        speed= (int)(SPEED_BASE * buffs.getSpeed() * 0.1f);
    }

    private boolean takeDamage() {
        Log.d("kek", "Player taking damage .");
        if(--health < 1) {
            getScene().getSounds().playSound(SoundType.LIFE_LOST);
            getScene().getGUI().updateLives(Integer.toString(health));
            getScene().levelFinished(GameState.PLAYER_DIED);
            return true;
        }
        getScene().getSounds().playSound(SoundType.LIFE_LOST);
        getScene().getGUI().updateLives(Integer.toString(health));
        return false;
    }

    @Override
    protected Rect getScreenRect() {
        return getScreen().getScreenRectPlayer(boundingRect);
    }

    @Override
    public void rescale(int oldTileSize) {
        super.rescale(oldTileSize);
        getScreen().setViewPos(getPosition());
    }

    private boolean isEnemyUnique(int enemyID) {
        long now= System.currentTimeMillis();
        if(enemyID != lastEnemy || now - enemyContactTimestamp >= 1500) {
            lastEnemy = enemyID;
            enemyContactTimestamp = now;
            return true;
        }
        return false;
    }

    public void addScore(int value) {
        score += value;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public Buffs getBuffs() {
        return buffs;
    }
}
