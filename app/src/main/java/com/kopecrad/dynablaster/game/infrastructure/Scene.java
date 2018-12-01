package com.kopecrad.dynablaster.game.infrastructure;

import android.util.Log;

import com.kopecrad.dynablaster.activity.GameActivity;
import com.kopecrad.dynablaster.game.infrastructure.level.LevelState;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelData;
import com.kopecrad.dynablaster.game.infrastructure.level.PlayerProgress;
import com.kopecrad.dynablaster.game.infrastructure.level.data.LevelLoader;
import com.kopecrad.dynablaster.game.infrastructure.score.Score;
import com.kopecrad.dynablaster.game.infrastructure.score.ScoreTableAccess;
import com.kopecrad.dynablaster.game.objects.GameObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private GUIHandle gui;
    private GameActivity context;

    public Scene(GameActivity context, PlayerProgress progress, Renderer renderer, GUIHandle gui) {
        this.context= context;
        this.gui= gui;
        this.progress= progress;
        this.renderer= renderer;
        this.imgRes= new ImageResources(context);
        AssetLoader.setManager(context.getAssets());
        this.levelLoader= new LevelLoader();
        GameObject.setSceneRef(this);
        LevelState.setSceneRef(this);

        Log.d("kek", "Test date string: " + generateDateString());

        startGame();
    }

    public boolean startGame() {
        if(levelData == null)
            levelData= levelLoader.loadLevel(progress.getNextLevel());

        if(levelData != null) {
            levelState = levelData.generateState(progress);
            renderer.registerRenderable(levelState);
            return true;
        }

        return false;
    }

    public LevelState getState() {
        return levelState;
    }

    public void levelFinished(GameState state) {
        switch (state) {
            case LEVEL_COMPLETED:
                Log.d("kek", "LEVEL FINISHED");
                levelState.updateProgress(progress);
                levelData= null;
                if(startGame())
                    return;
                break;
            case PLAYER_DIED:
            case TIMES_UP:
                Log.d("kek", "GAME OVER");
                break;
        }

        Score s= saveScoreOffline(progress);
        progress.resetProgress();
        context.switchToEnd(state, s);
    }

    public GUIHandle getGUI() {
        return gui;
    }

    private Score saveScoreOffline(PlayerProgress progress) {
        ScoreTableAccess score= new GameDB(context).getTableScore();
        Score s= new Score(
                "localPlayer",
                progress.getScore(),
                generateDateString(),
                progress.getLevelsCleared()
        );

        Log.d("kek", "Adding score entry: " + s.toString());
        score.addEntry(s);

        return s;
    }

    private String generateDateString() {
        return new SimpleDateFormat("dd. MM. yyyy", Locale.getDefault()).format(new Date());
    }
}
