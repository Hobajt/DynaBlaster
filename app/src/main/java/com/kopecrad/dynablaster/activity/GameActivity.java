package com.kopecrad.dynablaster.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.SurfaceView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.Renderer;
import com.kopecrad.dynablaster.game.infrastructure.Scene;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;

/**
 * Core game activity - where actual game happens.
 */
public class GameActivity extends FullscreenActivity {

    private Renderer renderer;
    private Scene scene;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        prefs= getSharedPreferences(getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE);

        SurfaceView surfView= findViewById(R.id.surfaceView);
        renderer= new Renderer(surfView);
        scene= new Scene(new PlayerProgress(prefs), renderer);
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
