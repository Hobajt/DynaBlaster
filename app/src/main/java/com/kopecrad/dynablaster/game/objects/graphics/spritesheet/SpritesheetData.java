package com.kopecrad.dynablaster.game.objects.graphics.spritesheet;

import android.graphics.Bitmap;
import android.graphics.Point;

public class SpritesheetData {

    private Point size;

    private int count;
    private int colCount;
    private int rowCount;

    public SpritesheetData(int colCount, int rowCount, int count) {
        this.count = count;
        this.colCount = colCount;
        this.rowCount = rowCount;
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
}
