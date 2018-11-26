package com.kopecrad.dynablaster.game.infrastructure;

import android.graphics.Point;
import android.util.Log;

public class InputHandler {

    private static ScreenSettings screen;
    Point moveDir;
    boolean moving;
    boolean bomb;

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

    public void playerInputEnded() {
        moving= false;
        moveDir= new Point(0,0);
    }

    public void playerInput(float x, float y) {
        Point p= screen.getScreenCenter();

        //prepocet pozice doteku do <-1,1>
        float xPos= (p.x - x) / p.x;
        float yPos= (p.y - y) / p.y;

        if(Math.abs(xPos) < 0.3f && Math.abs(yPos) < 0.3f) {
            //drop bomb
            Log.d("kek", "Dropping bomb");
            bomb= true;
            return;
        }

        moving= true;
        if(Math.abs(xPos) > Math.abs(yPos))
            moveDir= new Point(-(int)Math.signum(xPos), 0);
        else
            moveDir= new Point(0, -(int)Math.signum(yPos));
    }

    public static void setScreenRef(ScreenSettings scr) {
        screen= scr;
    }
}
