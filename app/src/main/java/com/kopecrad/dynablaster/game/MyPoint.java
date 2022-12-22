package com.kopecrad.dynablaster.game;

import android.graphics.Point;

public class MyPoint extends Point {

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MyPoint add(Point p) {
        return new MyPoint(x + p.x, y + p.y);
    }
}
