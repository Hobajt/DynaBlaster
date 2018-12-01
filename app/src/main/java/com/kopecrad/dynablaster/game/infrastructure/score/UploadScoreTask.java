package com.kopecrad.dynablaster.game.infrastructure.score;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadScoreTask extends AsyncTask<String, Void, Boolean> {

    private Score score;

    public UploadScoreTask(Score score) {
        this.score= score;
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        try {
            Gson gson = new Gson();
            sendDataPost(urls[0], gson.toJson(score));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("kek", "Something went wrong during score upload.");
            return false;
        }
        return true;
    }

    private void sendDataPost(String tgUrl, String data) throws IOException {
        URL url = new URL(tgUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        /*conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");*/
        conn.setDoOutput(true);
//        conn.setDoInput(true);
        conn.connect();

        Log.d("kek", "::::::UploadScoreTask::::::");

        Log.d("kek", "Data: " + data);
        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
        os.writeBytes(data);
        os.flush();
        os.close();

        Log.d("kek", "Status: " + String.valueOf(conn.getResponseCode()));
        Log.d("kek", "Message: " + conn.getResponseMessage());
        conn.disconnect();
    }
}
