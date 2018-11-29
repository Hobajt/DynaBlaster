package com.kopecrad.dynablaster.game.objects.collidable;

import java.util.Random;

public enum ItemType {
    FIRE,
    BOMB,
    SPEED,
    HEALTH;

    public static ItemType random() {
        Random r= new Random(System.nanoTime());
        return values()[r.nextInt(values().length)];
    }
}
