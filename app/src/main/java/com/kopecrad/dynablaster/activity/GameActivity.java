package com.kopecrad.dynablaster.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.InputHandler;
import com.kopecrad.dynablaster.game.infrastructure.Renderer;
import com.kopecrad.dynablaster.game.infrastructure.Scene;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelData;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;

/**
 * Core game activity - where actual game happens.
 */
public class GameActivity extends FullscreenActivity {

    private Renderer renderer;
    private Scene scene;
    private InputHandler inp;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        prefs= getSharedPreferences(getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE);

        SurfaceView surfView= findViewById(R.id.surfaceView);
        renderer= new Renderer(surfView);
        scene= new Scene(this, new PlayerProgress(prefs), renderer, (TextView) findViewById(R.id.playerHealth));
        inp= scene.getState().getInput();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inp.playerInput(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                inp.playerInputEnded();
                break;
        }

        return super.onTouchEvent(event);
    }
}
