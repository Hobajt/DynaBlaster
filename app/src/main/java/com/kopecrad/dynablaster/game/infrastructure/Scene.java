package com.kopecrad.dynablaster.game.infrastructure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.widget.TextView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.Renderable;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelData;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelLoader;
import com.kopecrad.dynablaster.game.objects.GameObject;

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
    private TextView lifeCounter;

    public Scene(Context context, PlayerProgress progress, Renderer renderer, TextView lifeCounter) {
        this.lifeCounter= lifeCounter;
        this.progress= progress;
        this.renderer= renderer;
        this.imgRes= new ImageResources(context);
        AssetLoader.setManager(context.getAssets());
        this.levelLoader= new LevelLoader();
        GameObject.setSceneRef(this);

        startGame();
    }

    public void startGame() {
        if(levelData == null)
            levelData= levelLoader.loadLevel(progress.getNextLevel());
        levelState= levelData.generateState(progress);
        renderer.registerRenderable(levelState);
    }

    public LevelState getState() {
        return levelState;
    }

    public void levelFinished(GameState playerDied) {
        //TODO: handle various level endings
        Log.d("kek", "GAME OVER");
    }

    public void setLifeCount(final int health) {
        lifeCounter.post(new Runnable() {
            @Override
            public void run() {
                lifeCounter.setText("Lives: " + health);
            }
        });
    }
}
