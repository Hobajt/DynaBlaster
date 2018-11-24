package com.kopecrad.dynablaster.game.infrastructure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Canvas draw updates - controlled in separate thread.
 * Don't use Thread.start() to initialize, use resume() instead.
 */
public class Renderer implements Runnable/*, SurfaceHolder.Callback  */{

    float pos;

    private Thread renderThread;
    private boolean running;

    private SurfaceView surfView;
    private SurfaceHolder holder;

    public Renderer(SurfaceView surfView) {
        this.surfView= surfView;
        holder= surfView.getHolder();
        //holder.addCallback(this);
        renderThread= null;
        running= false;
        pos= 0f;
    }

    @Override
    public void run() {
        while(running) {
            if(!holder.getSurface().isValid())
                continue;

            Canvas canvas= holder.lockCanvas();
            canvas.drawRGB(255, 0, 0);

            Point p= new Point();
            surfView.getDisplay().getSize(p);

            Paint paint= new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(p.x* pos, p.y* pos, p.x * pos + 20, p.y * pos + 20, paint);
            holder.unlockCanvasAndPost(canvas);

            pos += 0.02f;
            if(pos > 1.f)
                pos= 0.f;
        }
    }

    /**
     * Terminates the thread that manages this class.
     * Call from activity's onPause() or during termination.
     */
    public void pause() {
        Log.d("kek", "Renderer::pause");
        running= false;
        boolean ending= true;
        while(ending) {
            try {
                renderThread.join();
                ending= false;
            } catch (InterruptedException e) {}
        }
    }

    /**
     * Reactivates the thread that manages this class.
     * Call in activity's onResume()
     */
    public void resume() {
        Log.d("kek", "Renderer::resume");
        running= true;
        renderThread= new Thread(this);
        renderThread.start();
    }

    /*@Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d("kek", "Surface Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("kek", "Surface Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d("kek", "Surface Destroyed");
    }*/
}
