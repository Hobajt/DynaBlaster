package com.kopecrad.dynablaster.game.objects.collidable.creature;

import android.graphics.Point;

import com.kopecrad.dynablaster.game.objects.collidable.Bomb;

import java.util.ArrayList;
import java.util.List;

public class BombPool {

    private List<Bomb> bombs;
    private int bombPointer;

    private int bombCap;

    public BombPool() {
        bombs= new ArrayList<>();
        bombCap= 1;
        bombPointer= 0;

        balancePool();
    }

    /**
     * Initializes the pool for use in the game.
     */
    public void setup(int bombCap) {
        this.bombCap= bombCap;

        balancePool();
    }

    /**
     * Balances pool's bomb count - to match required values.
     */
    private void balancePool() {
        if(bombCap > bombs.size()) {
            //add new bombs
            for(int i= 0; i < bombCap - bombs.size(); i++) {
                bombs.add(new Bomb());
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

    public boolean hasFreeBombs() {
        Bomb b= bombs.get(nextBombIndex());
        return !b.isActive();
    }

    /**
     * Spawns new bomb and returns its reference.
     * Returns null when bomb cannot be spawned.
     */
    public Bomb dropBomb(Point position) {
        if(hasFreeBombs()) {
            Bomb b= bombs.get(nextBombIndex());
            b.activate(position);
            bombPointer++;
            return b;
        }
        return null;
    }

    private int nextBombIndex() {
        if(bombPointer+1 >= bombCap-1)
            return 0;
        return bombPointer+1;
    }
}
