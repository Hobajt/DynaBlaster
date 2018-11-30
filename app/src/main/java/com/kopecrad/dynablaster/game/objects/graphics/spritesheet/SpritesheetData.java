package com.kopecrad.dynablaster.game.objects.graphics.spritesheet;

import android.graphics.Bitmap;
import android.graphics.Point;

public class SpritesheetData {

    private int count;
    private int colCount;
    private int rowCount;

    private float speed;
    private String ghost;

    public SpritesheetData(int colCount, int rowCount, int count, int speedPercentage, String ghost) {
        this.count = count;
        this.colCount = colCount;
        this.rowCount = rowCount;
        this.speed= 100/speedPercentage;
        this.ghost= ghost;
    }

    public int getColCount() {
        return colCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public Point getImageSize(Bitmap sheet) {
        return new Point(sheet.getWidth()/colCount, sheet.getHeight()/rowCount);
    }

    public int getCount() {
        return count;
    }

    public float getSpeed() {
        return speed;
    }

    public String getGhost() {
        return ghost;
    }
}
