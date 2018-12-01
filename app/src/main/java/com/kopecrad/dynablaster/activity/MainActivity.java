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
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
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
        //TODO: add popup with question "Do you really want to reset your progress?"

        //TODO: erase achievements (scores, etc.)

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

        Intent intent= new Intent(getBaseContext(), GameActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
        soundSwitch.setChecked(prefs.getBoolean("sound", true));
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor pEdit= prefs.edit();
                pEdit.putBoolean("sound", compoundButton.isChecked());
                pEdit.apply();
                Log.d("kek", "Sound state changed.");
            }
        });
    }
}

