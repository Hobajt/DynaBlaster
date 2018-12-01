package com.kopecrad.dynablaster.game.infrastructure.score;

import java.util.List;

public interface ScoreContainer {

    List<Score> getScore();

    ScoreAdapter getAdapter();
}
