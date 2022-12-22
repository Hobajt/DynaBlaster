package com.kopecrad.dynablaster.game;

import android.graphics.Rect;

public class MyRect {

    public int left;
    public int right;
    public int bottom;
    public int top;

    public MyRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public Rect Rekt() {
        return new Rect(left, top ,right , bottom);
    }
}
