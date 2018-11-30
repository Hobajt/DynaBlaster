package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.infrastructure.ScreenSettings;
import com.kopecrad.dynablaster.game.infrastructure.level.EnemyDescription;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.objects.GameObject;
import com.kopecrad.dynablaster.game.objects.Updatable;
import com.kopecrad.dynablaster.game.objects.collidable.AnimPlayer;
import com.kopecrad.dynablaster.game.objects.collidable.CollidableRank;
import com.kopecrad.dynablaster.game.objects.graphics.Animation;
import com.kopecrad.dynablaster.game.objects.graphics.ObjectGraphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Creature {

    private static List<Point> pts;

    private static int enemyCounter= 0;

    private EnemyDescription desc;
    private int enemyID;

    private Point targetDir = null;
    private Point targetPos= null;
    private float lastDist= Float.POSITIVE_INFINITY;
    private int unstuckCounter= 0;

    public Enemy(EnemyDescription description) {
        super(0, 0, description.getGraphics());
        desc= description;
        enemyID= enemyCounter++;
        speed= (int)(SPEED_BASE * description.getSpeed() * 0.1f);

        if(pts == null) {
            pts= new ArrayList<>();
            pts.add(new Point(1,0));
            pts.add(new Point(-1,0));
            pts.add(new Point(0,1));
            pts.add(new Point(0,-1));
        }
    }

    public Enemy(int x, int y, ObjectGraphics texture) {
        super(x, y, texture);
    }

    @Override
    public CollidableRank getRank() {
        return CollidableRank.ENEMY;
    }

    protected boolean peerCollision(CollidableRank other, int fireID) {
        if(other != CollidableRank.FIRE)
            return false;

        //Log.d("kek", "Enemy collides with " + other.name() + ": " + fireID);
        if(isFireUnique(fireID)) {
            //possible spot to implement multi-health enemies
            Log.d("kek", "Enemy is dying!!!");
            return true;
        }
        return false;
    }

    @Override
    protected int getID() {
        return enemyID;
    }

    public void aiUpdate(GameObject[] map, Player player) {
        if(targetDir == null) {
            //choose new target tile for movement
            targetDir= chooseNewTarget(map);
        }
        else {
            float dist= getDistanceFrom(targetPos.x, targetPos.y);
            if(dist < ScreenSettings.TILE_SIZE_HALF*0.25f) {
                targetDir= chooseNewTarget(map);
                lastDist= Float.POSITIVE_INFINITY;
                unstuckCounter= 0;
            }
            else if(lastDist < dist) {
                if(unstuckCounter++ > 5) {
                    targetDir= chooseNewTarget(map);
                    lastDist= Float.POSITIVE_INFINITY;
                    unstuckCounter= 0;
                }
                lastDist= dist;
            }
            else
                lastDist= dist;
        }
        //Log.d("kek", "Dist: " + getDistanceFrom(targetPos.x, targetPos.y) + ", Dir: " + targetDir.toString());

        if(targetDir == null)
            return;

        //move to the target tile
        move(targetDir);
    }

    private Point chooseNewTarget(GameObject[] map) {
        Point currPos= getMapPos();

        Random rd= new Random(System.nanoTime());
        int startPos= rd.nextInt(pts.size());
        int i= startPos;
        do {
            Point p= pts.get(i);
            p= new Point(currPos.x + p.x, currPos.y + p.y);
            if(!state.getTileAt(p).isTraversable()) {
                if(++i >= pts.size())
                    i= 0;
                continue;
            }
            targetPos= getScreen().calcPosition(p.x, p.y);
            return pts.get(i);

        } while(i != startPos);

        return null;
    }

    /**
     * For dying animation implementation.
     * @return
     */
    public Updatable createGhost() {
        Animation ghost= getGhost();
        if(ghost != null)
            return new AnimPlayer(ghost, getScreenRect());
        return null;
    }
}
