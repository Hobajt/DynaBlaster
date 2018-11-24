package com.kopecrad.dynablaster.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.Renderer;
import com.kopecrad.dynablaster.game.infrastructure.Scene;

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

        LevelState state= loadGameState();

        SurfaceView surfView= findViewById(R.id.surfaceView);
        renderer= new Renderer(surfView);
        scene= new Scene(state, renderer);
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

    /**
     * Loads player's current progress (if there is any)
     */
    private LevelState loadGameState() {
        //check if resetGame flag is set -> reset game stats
        if(prefs.getBoolean("resetGame", false)) {
            //reset the progress
            Log.d("kek", "Resetting player's progress.");

            //override the flag
            SharedPreferences.Editor pEdit= prefs.edit();
            pEdit.putBoolean("resetGame", false);
            pEdit.apply();
        }

        //check for current progress data -> load it if available
        //TODO: progress data will proabably be in activity-only preferences


        //TODO: have to set somewhere gameInProgress flag 
        //else load initial setup
        return null;
    }
}
