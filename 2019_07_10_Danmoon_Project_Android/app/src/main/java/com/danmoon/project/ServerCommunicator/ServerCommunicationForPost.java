package com.danmoon.project.ServerCommunicator;

import android.os.AsyncTask;

import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.JsonParser.JsonParser;
import com.danmoon.project.Activity.MainActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ServerCommunicationForPost extends AsyncTask<String, Void, PostDto> {

    JSONObject postData;
    static final String CONNECT_URL = MainActivity.serverUrl;

    JsonParser jsonParser = new JsonParser();
    PostDto postDto = new PostDto();

    public ServerCommunicationForPost(Map<String, Object> postData){
        System.out.println("ServerCommunicationForPost");
        this.postData = new JSONObject(postData);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected PostDto doInBackground(String... params) {

        String response = "";
        String strUrl = CONNECT_URL + params[0];
        System.out.println(strUrl);
        try{
            URL url = new URL(strUrl);

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");

            //OPTIONAL
            urlConnection.setRequestProperty("Authorization", "someAuthString");
            System.out.println(params[0]);
            System.out.println(postData.toString());

            if(this.postData != null){
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                System.out.println(postData.toString());
                writer.flush();
            }

            int statusCode = urlConnection.getResponseCode();
            System.out.println("statusCode : "  + statusCode);
            if(statusCode == 200) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);

                System.out.println("서버로부터의 응답");
                System.out.println(response);

            } else {

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return postDto;
    }

    private String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(PostDto s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(PostDto s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

