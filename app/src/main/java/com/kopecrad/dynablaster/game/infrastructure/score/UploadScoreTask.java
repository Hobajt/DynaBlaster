package com.kopecrad.dynablaster.game.infrastructure.score;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UploadScoreTask extends AsyncTask<String, Void, Boolean> {

    private Score score;

    public UploadScoreTask(Score score) {
        this.score= score;
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        try {
            sendDataPost(urls[0], prepareData(score));
        } catch(FileNotFoundException e) {
            Log.d("scTask", "BAD REQUEST response");
            return false;
        } catch (IOException e) {
            Log.d("scTask", ":::::::::::::::::::::::::::STRACE::::::::::::::::::::::::");
            e.printStackTrace();
            Log.d("scTask", ":::::::::::::::::::::::::__STRACE__::::::::::::::::::::::");
            Log.d("scTask", "Something went wrong during score upload.");
            return false;
        }
        return true;
    }

    private void sendDataPost(String tgUrl, String data) throws IOException {
        URL url = new URL(tgUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            Log.d("scTask", "::::::UploadScoreTask::::::");
            conn.setFixedLengthStreamingMode(data.length());
            conn.setDoOutput(true);
            conn.connect();

            Log.d("scTask", "Data: " + data);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(data);
            os.flush();
            os.close();

            //only for debug, no real need to read response
            InputStream in= new BufferedInputStream(conn.getInputStream());
            String s= readJson(in);

            Log.d("scTask", "Status: " + String.valueOf(conn.getResponseCode()));
            Log.d("scTask", "Message: " + conn.getResponseMessage());
            Log.d("scTask", "Response: " + s);
        } finally {
            conn.disconnect();
        }
    }

    private String prepareData(Score score) throws UnsupportedEncodingException {
        Map<String, String> params= new HashMap<>();
        params.put("player", score.getPlayer());
        params.put("score", score.getScore());
        params.put("date", score.getDate());
        params.put("levels", score.getLevels());
        return mapToPostData(params);
    }

    private String mapToPostData(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
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
}
