package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.game.objects.collidable.Bomb;

import java.util.ArrayList;
import java.util.List;

public class BombPool {

    private List<Bomb> bombs;
    private int bombPointer;

    private int bombCap;

    public BombPool() {
        this(1);
    }

    public BombPool(int cap) {
        bombs= new ArrayList<>();
        bombCap= cap;
        bombPointer= 0;

        balancePool();
    }

    /**
     * Initializes the pool for use in the game.
     */
    public void setup(int bombCap) {
        this.bombCap= bombCap;

        balancePool();
        Log.d("bmbTst", "Bomb pool size: " + bombs.size() + "; " + bombCap);
    }

    /**
     * Balances pool's bomb count - to match required values.
     */
    private void balancePool() {
        if(bombCap > bombs.size()) {
            //add new bombs
            for(int i= 0; i < bombCap - bombs.size(); i++) {
                bombs.add(new Bomb());
                Log.d("bmbTst", "Initial bomb added");
            }
        }
        else if(bombCap < bombs.size()) {
            //remove redundant bomb instances
            List<Bomb> b= new ArrayList<>();
            for(int i= 0; i < bombCap; i++)
                b.add(bombs.get(i));
            bombs= b;
        }
        resetBombs();
    }

    /**
     * Resets the state of all bombs.
     */
    private void resetBombs() {
        for(Bomb b : bombs)
            b.deactivate();
    }

    public boolean isBombFree() {
        Bomb b= bombs.get(bombPointer);
        return !b.isActive();
    }

    /**
     * Spawns new bomb and returns its reference.
     * Returns null when bomb cannot be spawned.
     */
    public Bomb dropBomb(Point position) {
        Bomb b= bombs.get(bombPointer);
        //System.out.println("ptr: " + bombPointer + ", cap: " + bombCap + ", size: " + bombs.size());
        if(!b.isActive()) {
            Log.d("bmbTst", "Dropping bomb " + bombPointer);
            b.activate(position);
            if(++bombPointer >= bombs.size())
                bombPointer= 0;
            return b;
        }
        return null;
    }

    private int nextBombIndex() {
        if(bombPointer+1 >= bombCap-1)
            return 0;
        return bombPointer+1;
    }

    public void updateCount(int bombCap) {
        this.bombCap= bombCap;
        Log.d("bmbTst", "BOmbCap: " + this.bombCap);
        if(bombCap > bombs.size()) {
            Log.d("bmbTst", "Gotta add new bombs");
            //add new bombs
            for(int i= 0; i < bombCap - bombs.size(); i++) {
                bombs.add(new Bomb());
            }
            if(bombs.get(bombPointer).isActive())
                bombPointer++;
        }
    }
}
