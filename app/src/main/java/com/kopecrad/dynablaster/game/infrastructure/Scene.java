package com.kopecrad.dynablaster.game.infrastructure;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;

/**
 * Core game management.
 * Manages currently active level.
 */
public class Scene {

    private LevelState state;
    private Renderer renderer;

    public Scene(LevelState state, Renderer renderer) {
        this.state= state;
        this.renderer= renderer;
    }
}
