package com.kopecrad.dynablaster.game.objects.creature;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;
import com.kopecrad.dynablaster.game.objects.obstacle.Bomb;
import com.kopecrad.dynablaster.game.objects.obstacle.Obstacle;
import com.kopecrad.dynablaster.game.objects.tile.Tile;

import java.util.List;

public class Player extends Creature {

    private List<Bomb> bombPool;
    private int bombPointer;

    private int moveVec;
    //private boolean xPositive, yPositive;
    private Point colTile;

    public Player(Point pos, Animation anim) {
        super(pos.x, pos.y, anim);
        colTile= getMapPos();
    }

    public void move(Point moveDir) {
        realPos= new Point(realPos.x + moveDir.y * speed, realPos.y + moveDir.x * speed);

        moveY= moveDir.y == 1;
        moveVec= moveY ? moveDir.y : moveDir.x;
        updateTile();
        collisionTile(moveY);
    }

    private void collisionTile(boolean moveY) {
        if(moveY)
            colTile= new Point(getMapPos().x, getMapPos().y + moveVec);
        else
            colTile= new Point(getMapPos().x + moveVec, getMapPos().y);
    }

    /**
     * Updates nearest tile index.
     * Use for collision detection
     */
    private void updateTile() {
        Point p= getMapPos();
        if(moveY) {
            Log.d("kek", "yasedf " + moveY);
            if(Math.abs(realPos.y - ((p.y + moveVec) * ScreenSettings.TILE_SIZE)) < ScreenSettings.TILE_SIZE_HALF) {
                setPosition(new Point(p.x, p.y + moveVec));
                p= getMapPos();
                Log.d("kek", "Newposo: ("+ p.x +", "+ p.y +")");
            }
        }
        else {
            if(Math.abs(realPos.x - ((p.x + moveVec) * ScreenSettings.TILE_SIZE)) < ScreenSettings.TILE_SIZE_HALF) {
                setPosition(new Point(p.x + moveVec, p.y));
                p= getMapPos();
                Log.d("kek", "Newpos: ("+ p.x +", "+ p.y +")");
            }
        }
    }

    @Override
    public void checkCollision(LevelState state) {
        try {
            Obstacle o= (Obstacle) state.getTileAt(colTile);
            if(o.isColliding(this)) {
                revertMove();
                Log.d("kek", "Colliding");
            }

        } catch (ClassCastException e) {}
    }

    @Override
    public void revertMove() {
        if(moveY)
            realPos.y= getMapPos().y * ScreenSettings.TILE_SIZE;
        else
            realPos.x= getMapPos().x * ScreenSettings.TILE_SIZE;
    }
}
