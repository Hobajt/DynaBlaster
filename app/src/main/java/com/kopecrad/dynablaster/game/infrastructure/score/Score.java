package com.kopecrad.dynablaster.game.infrastructure.score;

import com.google.gson.Gson;

public class Score {

    private String player;
    private int score;
    private String date;

    public Score(String player, int score, String date) {
        this.player = player;
        this.score = score;
        this.date = date;
    }

    public String getPlayer() {
        return player;
    }

    public String getScore() {
        return Integer.toString(score);
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
