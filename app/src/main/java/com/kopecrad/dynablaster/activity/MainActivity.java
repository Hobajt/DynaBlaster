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
import android.widget.TextView;

import com.kopecrad.dynablaster.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity with main game menu.
 * Provides means to start the game and modify game options.
 */
public class MainActivity extends FullscreenActivity {

    private Map<String, View> menus;
    private SharedPreferences prefs;

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
     * Starts the game.
      * @param view
     */
    public void startGame(View view) {
        Intent intent= new Intent(getBaseContext(), GameActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Removes any game progres.
     * @param view
     */
    public void resetGame(View view) {
        //TODO: add popup with question "Do you really want to reset your progress?"

        //write reset flag - actual reset happens in GameActivity during level loading
        SharedPreferences.Editor pEdit= prefs.edit();
        pEdit.putBoolean("resetGame", true);
        pEdit.apply();

        //rename start game btn to default
        ((TextView)findViewById(R.id.menu_main_startGameBtn))
                .setText(getResources().getString(R.string.menu_startGame));
        Log.d("kek", "Game progress has been reset.");
    }

    /**
     * Starts activity with offline & online scoreboards display.
     * @param view
     */
    public void showScoreboard(View view) {
        //TODO: implement scoreboard activity
    }

    /**
     * Activity initialization in general.
     */
    private void initialize() {
        //load menu slides references
        menus= new HashMap<>();
        menus.put("main", findViewById(R.id.menu_mainLayout));
        menus.put("options", findViewById(R.id.menu_optionsLayout));

        //init preferences
        prefs= getSharedPreferences(getResources().getString(R.string.prefs_name), Context.MODE_PRIVATE);

        //initialize sound switch in options
        init_soundSwitch();

        //rename start game button
        init_renameStartButton();
    }

    private void init_renameStartButton() {
        TextView btn= findViewById(R.id.menu_main_startGameBtn);
        btn.setText( getResources().getString(
                prefs.getBoolean("gameInProgress", false)
                        ? R.string.menu_startGameContinue
                        : R.string.menu_startGame)
        );
    }

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
