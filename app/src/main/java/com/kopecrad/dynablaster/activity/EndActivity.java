package com.kopecrad.dynablaster.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kopecrad.dynablaster.R;
import com.kopecrad.dynablaster.game.infrastructure.GameState;
import com.kopecrad.dynablaster.game.infrastructure.score.Score;
import com.kopecrad.dynablaster.game.infrastructure.score.UploadScoreTask;

/**
 * Activity that shows up after player dies/finishes the game.
 */
public class EndActivity extends FullscreenActivity {

    public static final String URL = "https://homel.vsb.cz/~kop0203/tamz2/scoreboards.php";

    private GameState state;
    private Score saveScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        loadData();
        setupLayout();
    }

    private void setupLayout() {
        TextView msg= findViewById(R.id.endGameMessage);
        LinearLayout scoreLayout= findViewById(R.id.end_scorePopup);
        ImageView imv= findViewById(R.id.menu_end_image);
        imv.setImageResource(
                state == GameState.LEVEL_COMPLETED
                        ? R.drawable.end_win
                        : R.drawable.end_lost
        );


        switch (state) {
            case LEVEL_COMPLETED:
                msg.setText(getString(R.string.menu_endText_won));
                if(saveScore != null) {
                    scoreLayout.setVisibility(View.VISIBLE);
                }
                break;
            case PLAYER_DIED:
                msg.setText(getString(R.string.menu_endText_lostHealth));
                break;
            case TIMES_UP:
                msg.setText(getString(R.string.menu_endText_lostTime));
                break;
        }


    }

    private void loadData() {
        state= GameState.values()[(
                getIntent().getIntExtra("state", GameState.PLAYER_DIED.ordinal())
        )];
        String s= getIntent().getStringExtra("score");
        if(s != null) {
            Gson gson= new Gson();
            saveScore = gson.fromJson(s, Score.class);
        }
    }

    public void backToMain(View view) {
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void saveScore(View view) {
        String nickname= getNickname();
        if(nickname.length() < 3) {
            Log.d("kek", "Nickname has to be at least 3 characters long.");
            //show error message
            return;
        }

        saveScore.setPlayer(nickname);
        LinearLayout scoreLayout= findViewById(R.id.end_scorePopup);
        scoreLayout.setVisibility(View.INVISIBLE);
        new UploadScoreTask(saveScore).execute(URL);
    }

    private String getNickname() {
        EditText text= findViewById(R.id.menu_end_nameInput);
        return text.getText().toString();
    }
}
