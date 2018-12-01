package com.kopecrad.dynablaster.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.GUIHandle;
import com.kopecrad.dynablaster.game.infrastructure.GameDB;
import com.kopecrad.dynablaster.game.infrastructure.GameState;
import com.kopecrad.dynablaster.game.infrastructure.InputHandler;
import com.kopecrad.dynablaster.game.infrastructure.Renderer;
import com.kopecrad.dynablaster.game.infrastructure.Scene;
import com.kopecrad.dynablaster.game.infrastructure.level.EnemyDescription;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;

/**
 * Core game activity - where actual game happens.
 */
public class GameActivity extends FullscreenActivity {

    private Renderer renderer;
    private Scene scene;
    private SurfaceView surfView;
    private InputHandler inp;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        prefs= getSharedPreferences(getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE);

        setupInputListeners();

        surfView= findViewById(R.id.surfaceView);
        renderer= new Renderer(surfView);
        EnemyDescription.setEnemyTableRef(new GameDB(this).getTableEnemy());
        GUIHandle gameGUI= new GUIHandle(
                (TextView) findViewById(R.id.panel_livesTxt),
                (TextView) findViewById(R.id.panel_clockTxt),
                (TextView) findViewById(R.id.panel_scoreTxt)
        );
        scene= new Scene(this, new PlayerProgress(prefs), renderer, gameGUI);
        inp= InputHandler.inst();
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

    private void setupInputListeners() {
        setupMoveButton(R.id.btn_left);
        setupMoveButton(R.id.btn_right);
        setupMoveButton(R.id.btn_up);
        setupMoveButton(R.id.btn_down);
        setupMoveButton(R.id.btn_bomb);
    }

    private void setupMoveButton(final int id) {
        ImageView btn= findViewById(id);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    inp.setInput(id, true);
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    inp.setInput(id, false);
                }
                return false;
            }
        });
    }

    public void switchToEnd(final GameState state) {
        final GameActivity ac= this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(ac, EndActivity.class);
                intent.putExtra("state", state.ordinal());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ac).toBundle());
            }
        });
    }
}
