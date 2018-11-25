package com.kopecrad.dynablaster.game.infrastructure.level;

public class WinConditions {

    private float timeLimit;
    private WinGoal goal;

    private enum WinGoal {
        ELIMINATE_PORTAL,
        ELIMINATE,
        REMOVE_BLOCKS,
        BOSS
    }
}
