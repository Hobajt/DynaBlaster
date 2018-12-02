package com.kopecrad.dynablaster.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.GameDB;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.score.Score;
import com.kopecrad.dynablaster.game.infrastructure.score.UploadScoreTask;
import com.kopecrad.dynablaster.game.infrastructure.sound.SoundController;
import com.kopecrad.dynablaster.game.infrastructure.sound.SoundType;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity with main game menu.
 * Provides means to start the game and modify game options.
 */
public class MainActivity extends FullscreenActivity {

    private Map<String, View> menus;
    private SharedPreferences prefs;

    private boolean isProgress;
    private SoundController sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sounds.release();
        Log.d("kek", "MainActivity::onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sounds= new SoundController(SoundType.BG_TITLE, this);
        isProgress= !PlayerProgress.noProgress(prefs);
        continueBtnVisibility();
    }

    /**
     * Returns from any menu to the main menu
     * @param view
     */
    public void backToMain(View view) {
        menus.get("options").setVisibility(View.GONE);
        menus.get("main").setVisibility(View.VISIBLE);
    }

    /**
     * Opens options submenu
     * @param view
     */
    public void openOptions(View view) {
        menus.get("options").setVisibility(View.VISIBLE);
        menus.get("main").setVisibility(View.GONE);
    }

    /**
     * Completely removes local progress and achievements.
     * @param view
     */
    public void resetGame(View view) {
        //erase last game run
        PlayerProgress.resetProgress(prefs);
        isProgress= false;
        continueBtnVisibility();

        Log.d("kek", "Game progress has been reset.");
    }

    /**
     * Starts activity with offline & online scoreboards display.
     * @param view
     */
    public void showScoreboard(View view) {
        Intent intent= new Intent(this, ScoreboardsActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Starts the game with previous player's progress.
     * Should only be available when there is some progress.
     * @param view
     */
    public void gameContinue(View view) {
        sounds.release();
        Intent intent= new Intent(getBaseContext(), GameActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Starts brand new game run.
     * @param view
     */
    public void gameNew(View view) {
        if(isProgress) {
            PlayerProgress.resetProgress(prefs);
            isProgress= false;
        }

        sounds.release();
        Intent intent= new Intent(getBaseContext(), GameActivity.class);
        startActivity(intent);
        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Initial setup.
     * Sets proper button states, etc.
     */
    private void initialize() {
        //load menu slides references
        menus= new HashMap<>();
        menus.put("main", findViewById(R.id.menu_mainLayout));
        menus.put("options", findViewById(R.id.menu_optionsLayout));

        //init preferences
        prefs= getSharedPreferences(getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE);
        isProgress= !PlayerProgress.noProgress(prefs);


        //initialize sound switch in options
        init_soundSwitch();
        continueBtnVisibility();
    }

    /**
     * Hides continue button when there is no game in progress.
     */
    private void continueBtnVisibility() {
        findViewById(R.id.menu_main_gameContinueBtn).setVisibility(
            isProgress ? View.VISIBLE : View.GONE
        );
    }

    /**
     * Properly sets state of options-sound switch
     */
    private void init_soundSwitch() {
        Switch soundSwitch= findViewById(R.id.menu_opt_soundSwitch);
        boolean enabled= prefs.getBoolean("sound", true);
        SoundController.setMuted(!enabled);
        soundSwitch.setChecked(enabled);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor pEdit= prefs.edit();
                pEdit.putBoolean("sound", compoundButton.isChecked());
                SoundController.setMuted(!compoundButton.isChecked());
                sounds.mutedStateChange();
                pEdit.apply();
                Log.d("kek", "Sound state changed.");
            }
        });
    }

    public void resetScoreboards(View view) {
        new GameDB(this).getTableScore().removeAllEntries();
        Log.d("kek", "All offline score entries have been removed.");
    }

    public void quitGame(View view) {
        finish();
        System.exit(0);
    }
}

