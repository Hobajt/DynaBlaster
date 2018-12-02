package com.kopecrad.dynablaster.game.infrastructure.score;

import android.content.Context;
import android.util.Log;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.activity.ScoreboardsActivity;

import java.util.ArrayList;
import java.util.List;

public class ScoreOnlineContainer implements ScoreContainer {

    public static final String URL= "https://homel.vsb.cz/~kop0203/tamz2/scoreboards.json";

    private ScoreboardsActivity context;

    private List<Score> cache;
    private boolean failedOrEmpty;

    public ScoreOnlineContainer(ScoreboardsActivity context) {
        this.context = context;
    }

    @Override
    public List<Score> getScore() {
        if(cache == null || failedOrEmpty) {
            new DownloadScoreTask(context, this).execute(URL);
            return new ArrayList<>();
        }

        return cache;
    }

    @Override
    public ScoreAdapter getAdapter() {
        List<Score> data= getScore();
        return new ScoreAdapter(context, R.layout.list_score_layout, data);
    }

    public void downloadTaskCallback(List<Score> scores) {
        if(scores != null) {
            failedOrEmpty = scores.size() < 1;
            this.cache= scores;
        }
        else {
            failedOrEmpty= true;
            this.cache= new ArrayList<>();
            Log.d("kek", "Score list was null");
        }
        context.lateAdapter(new ScoreAdapter(context, R.layout.list_score_layout, cache));
    }
}
