package com.kopecrad.dynablaster.game.infrastructure.score;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DownloadScoreTask extends AsyncTask<String, Void, List<Score>> {

    Context context;
    ScoreOnlineContainer scoreContainer;

    public DownloadScoreTask(Context context, ScoreOnlineContainer scoreContainer) {
        this.context= context;
        this.scoreContainer= scoreContainer;
    }

    @Override
    protected List<Score> doInBackground(String... urls) {
        try {
            Log.d("scoreboards", "--starting DownloadScoreTask--");
            return loadScoreFromNetwork(urls[0]);
        } catch (IOException e) {
            Log.d("scoreboards", "failed to load, exception occured");
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Score> scores) {
        if(scores != null)
            Log.d("scoreboards" , "Score loaded (" + scores.size() + " entries)");
        else
            Log.d("scoreboards" , "Scores failed to load.");
        scoreContainer.downloadTaskCallback(scores);
    }

    private List<Score> loadScoreFromNetwork(String url) throws IOException {
        String json = readJson(downloadUrl(url));

        if(json == null) {
            Log.d("scoreboards", "failed to load data.");
            return null;
        }

        try {
            Gson gson = new Gson();
            Score[] sc= gson.fromJson(json, Score[].class);
            List<Score> scores = new ArrayList<>(Arrays.asList(sc));

            Log.d("scoreboards", "Returning scores (count: " + scores.size() + ")");
            return scores;
        } catch (JsonIOException | JsonSyntaxException e) {
            Log.d("kek", json);
            Log.d("scoreboards", "Score JSON deserialization failed");
            e.printStackTrace();
            return null;
        }
    }

    private String readJson(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }


}
