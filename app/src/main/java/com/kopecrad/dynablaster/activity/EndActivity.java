package com.kopecrad.dynablaster.activity;

import android.app.Activity;
import android.os.Bundle;

import com.kopecrad.dynablaster.R;

/**
 * Activity that shows up after player dies/finishes the game.
 */
public class EndActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
    }


}
