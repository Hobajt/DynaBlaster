package com.kopecrad.dynablaster.game.infrastructure.score;

import android.content.Context;
import android.util.Log;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.GameDB;

import java.util.ArrayList;
import java.util.List;

public class ScoreOfflineContainer implements ScoreContainer {

    private Context context;
    private ScoreTableAccess db;

    private List<Score> cache;

    private boolean failedOrEmpty;

    public ScoreOfflineContainer(Context context) {
        this.context= context;
        db= new GameDB(context).getTableScore();
        failedOrEmpty= false;
    }

    @Override
    public List<Score> getScore() {
        if(cache == null || failedOrEmpty) {
            cache= new ArrayList<>();
            if(!db.loadAllScores(cache)) {
                Log.d("kek", "ScoreDB:: failed to load player scoreboards");
                failedOrEmpty= true;
            }
        }
        return cache;
    }

    @Override
    public ScoreAdapter getAdapter() {
        List<Score> data= getScore();
        return new ScoreAdapter(context, R.layout.list_score_layout, data);
    }
}
