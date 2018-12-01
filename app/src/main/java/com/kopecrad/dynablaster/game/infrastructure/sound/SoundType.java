package com.kopecrad.dynablaster.game.infrastructure.sound;

import com.kopecrad.dynablaster.R;

public enum SoundType {
    BG_TITLE(R.raw.bg_title_screen),
    BG_LEVEL(R.raw.bg_level),
    LIFE_LOST (R.raw.life_lost),
    EXPLOSION (R.raw.explosion),
    GAME_OVER (R.raw.game_over),
    GAME_WON (R.raw.game_won);

    private final int resID;

    SoundType(int resID) {
        this.resID = resID;
    }

    public int getResID() {
        return resID;
    }
}