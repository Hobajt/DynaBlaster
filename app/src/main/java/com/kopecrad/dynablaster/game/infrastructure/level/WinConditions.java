package com.kopecrad.dynablaster.game.infrastructure.level;

public class WinConditions {

    private int timeLimit;
    private WinGoal goal;

    public WinConditions(int timeLimit, WinGoal goal) {
        this.timeLimit = timeLimit;
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "Time: " + timeLimit + ", Goal: " + goal.name();
    }

    public enum WinGoal {
        ELIMINATE_PORTAL,
        ELIMINATE,
        REMOVE_BLOCKS,
        BOSS
    }
}
