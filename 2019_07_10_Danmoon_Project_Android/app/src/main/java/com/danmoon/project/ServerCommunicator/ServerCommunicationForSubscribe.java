package com.danmoon.project.ServerCommunicator;

import android.os.AsyncTask;

import com.danmoon.project.DTO.SubscribeDto;
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
import java.util.ArrayList;
import java.util.Map;

public class ServerCommunicationForSubscribe extends AsyncTask<String, Void, ArrayList<SubscribeDto>> {

    JSONObject subscribeData;
    int idx;
    static final String CONNECT_URL = MainActivity.serverUrl;

    JsonParser jsonParser = new JsonParser();
    SubscribeDto subscribeDto = new SubscribeDto();
    ArrayList<SubscribeDto> returnArrForSubscribeList;

    public ServerCommunicationForSubscribe(){
        System.out.println("ServerCommunicationForSubscribe");
    }

    public ServerCommunicationForSubscribe(Map<String, Object> subscribeData){
        System.out.println("ServerCommunicationForSubscribe");
        this.subscribeData = new JSONObject(subscribeData);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<SubscribeDto> doInBackground(String... params) {

        returnArrForSubscribeList = new ArrayList<>();

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
//            System.out.println(postData.toString());

            if(this.subscribeData != null){
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
//                writer.write(postData.toString());
//                System.out.println(postData.toString());
                writer.flush();
            }

            int statusCode = urlConnection.getResponseCode();
            System.out.println("statusCode : "  + statusCode);
            if(statusCode == 200) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);

                System.out.println("서버로부터의 응답");
                System.out.println(response);

                returnArrForSubscribeList = jsonParser.jsonParserForSubscribeList(response);

            } else {

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return returnArrForSubscribeList;
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
    protected void onPostExecute(ArrayList<SubscribeDto> s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<SubscribeDto> s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
