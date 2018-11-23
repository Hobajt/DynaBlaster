package com.kopecrad.dynablaster;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameActivity extends FullscreenActivity {

    private Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SurfaceView surfView= findViewById(R.id.surfaceView);
        renderer= new Renderer(surfView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        renderer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        renderer.resume();
    }
}
