package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelData;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;

/**
 * Core game management.
 * Manages currently active level.
 */
public class Scene {

    private Renderer renderer;

    private PlayerProgress progress;
    private LevelData levelData;

    public Scene(PlayerProgress progress, Renderer renderer) {
        this.progress= progress;
        this.renderer= renderer;

        levelData= LevelData.LoadLevel(progress.getNextLevel());
    }
}
