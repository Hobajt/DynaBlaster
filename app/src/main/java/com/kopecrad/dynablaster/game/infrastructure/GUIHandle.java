package com.kopecrad.dynablaster.game.infrastructure;

import android.widget.TextView;

public class GUIHandle {

    private TextView gui_lives;
    private TextView gui_clock;
    private TextView gui_score;

    public GUIHandle(TextView gui_lives, TextView gui_clock, TextView gui_score) {
        this.gui_lives = gui_lives;
        this.gui_clock = gui_clock;
        this.gui_score = gui_score;
    }

    public void update(int lives, int score, String clock) {
        updateLives(Integer.toString(lives));
        updateClock(clock);
        updateScore(Integer.toString(score));
    }

    public void updateLives(final String livesTxt) {
        gui_lives.post(new Runnable() {
            @Override
            public void run() {
                gui_lives.setText(livesTxt);
            }
        });
    }

    public void updateClock(final String clockTxt) {
        gui_clock.post(new Runnable() {
            @Override
            public void run() {
                gui_clock.setText(clockTxt);
            }
        });
    }

    public void updateScore(final String scoreTxt) {
        gui_score.post(new Runnable() {
            @Override
            public void run() {
                gui_score.setText(scoreTxt);
            }
        });
    }
}
