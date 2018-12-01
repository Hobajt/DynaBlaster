package com.kopecrad.dynablaster.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.score.ScoreAdapter;
import com.kopecrad.dynablaster.game.infrastructure.score.ScoreContainer;
import com.kopecrad.dynablaster.game.infrastructure.score.ScoreOfflineContainer;
import com.kopecrad.dynablaster.game.infrastructure.score.ScoreOnlineContainer;

public class ScoreboardsActivity extends FullscreenActivity {

    private boolean showOnline;

    private ScoreOfflineContainer scoreOffline;
    private ScoreOnlineContainer scoreOnline;

    private ScoreContainer score;
    private ListView scoreList;

    private Button swapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboards);

        swapBtn= findViewById(R.id.score_switchModeBtn);
        scoreList= findViewById(R.id.score_listView);
        scoreOffline= new ScoreOfflineContainer(this);
        scoreOnline= new ScoreOnlineContainer(this);

        selectSource(getIntent().getBooleanExtra("showOnline", false));
        showScoreboard();
    }

    private void selectSource(boolean online) {
        showOnline= online;
        if(showOnline)
            score= scoreOnline;
        else
            score= scoreOffline;
        swapBtnText();
    }

    public void lateAdapter(ScoreAdapter adapter) {
        if(showOnline) {
            scoreList.setAdapter(adapter);
            scoreList.setSelectionFromTop(0, 0);
        }
    }

    private void showScoreboard() {
        ScoreAdapter sa= score.getAdapter();
        scoreList.setAdapter(sa);
        scoreList.setSelectionFromTop(0, 0);
        //scoreList.setOnItemClickListener(listener);
    }

    public void switchMode(View view) {
        selectSource(!showOnline);
        showScoreboard();
    }

    private void swapBtnText() {
        swapBtn.setText(getString(showOnline ? R.string.score_switchMode_off : R.string.score_switchMode_on));
    }

    public void backToMenu(View view) {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
