package com.kopecrad.dynablaster.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.GameState;

/**
 * Activity that shows up after player dies/finishes the game.
 */
public class EndActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        GameState state= GameState.values()[(
                getIntent().getIntExtra("state", GameState.PLAYER_DIED.ordinal())
        )];

        TextView msg= findViewById(R.id.endGameMessage);
        switch (state) {
            case LEVEL_COMPLETED:
                msg.setText(getString(R.string.menu_endText_won));
                break;
            case PLAYER_DIED:
                msg.setText(getString(R.string.menu_endText_lostHealth));
                break;
            case TIMES_UP:
                msg.setText(getString(R.string.menu_endText_lostTime));
                break;
        }
    }


    public void backToMain(View view) {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
