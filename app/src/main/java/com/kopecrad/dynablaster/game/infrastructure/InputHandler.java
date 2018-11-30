package com.kopecrad.dynablaster.game.infrastructure;

import android.graphics.Point;
import android.util.Log;

import com.kopecrad.dynablaster.R;

public class InputHandler {

    private static InputHandler instance;

    private static ScreenSettings screen;
    Point moveDir;
    boolean moving;
    boolean bomb;

    int yAxis= 0;
    int xAxis= 0;

    public static InputHandler inst() {
        if(instance == null)
            instance= new InputHandler();
        return instance;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean bombSignal() {
        boolean bmb= bomb;
        bomb= false;
        return bmb;
    }

    public Point getMoveDir() {
        return moveDir;
    }

    public static void setScreenRef(ScreenSettings scr) {
        screen= scr;
    }

    public void setInput(int id, boolean value) {
        switch (id) {
            case R.id.btn_bomb:
                if(value) {
                    Log.d("kek", "Dropping bomb");
                    bomb= true;
                }
                break;
            case R.id.btn_left:
                xAxis= value ? -1 : 0;
                axisUpdate();
                break;
            case R.id.btn_right:
                xAxis= value ? 1 : 0;
                axisUpdate();
                break;
            case R.id.btn_up:
                yAxis= value ? -1 : 0;
                axisUpdate();
                break;
            case R.id.btn_down:
                yAxis= value ? 1 : 0;
                axisUpdate();
                break;
        }
    }

    public void axisUpdate() {
        if(xAxis == 0 && yAxis == 0) {
            moveDir= new Point(0,0);
            moving= false;
            return;
        }

        moving= true;
        moveDir= new Point(xAxis, yAxis);
    }
}
