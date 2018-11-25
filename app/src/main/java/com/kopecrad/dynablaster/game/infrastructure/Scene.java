package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;

import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.Renderable;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelData;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelLoader;

/**
 * Core game management.
 * Manages currently active level.
 */
public class Scene {

    private Renderer renderer;
    private ImageResources imgRes;

    private LevelLoader levelLoader;
    private LevelData levelData;
    private LevelState levelState;

    private PlayerProgress progress;

    public Scene(Context context, PlayerProgress progress, Renderer renderer) {
        this.progress= progress;
        this.renderer= renderer;
        this.imgRes= new ImageResources(context);
        this.levelLoader= new LevelLoader(context.getAssets());

        startGame();
    }

    public void startGame() {
        if(levelData == null)
            levelData= levelLoader.loadLevel(progress.getNextLevel());
        levelState= levelData.generateState();
        renderer.registerRenderable(levelState);
    }
}
