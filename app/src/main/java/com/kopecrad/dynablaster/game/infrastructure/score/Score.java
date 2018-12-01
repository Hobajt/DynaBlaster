package com.kopecrad.dynablaster.game.infrastructure.score;

import com.google.gson.Gson;

public class Score {

    private String player;
    private int score;
    private String date;
    private int levels;

    public Score(String player, int score, String date, int levels) {
        this.player = player;
        this.score = score;
        this.date = date;
        this.levels = levels;
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

    public String getLevels() {
        return Integer.toString(levels);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void setPlayer(String nickname) {
        this.player= nickname;
    }
}
