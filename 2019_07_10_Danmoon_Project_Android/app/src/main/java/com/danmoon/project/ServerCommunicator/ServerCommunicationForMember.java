package com.danmoon.project.ServerCommunicator;

import android.os.AsyncTask;

import com.danmoon.project.DTO.MemberDto;
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

public class ServerCommunicationForMember extends AsyncTask<String, Void, MemberDto> {

    JSONObject postData;
    static final String CONNECT_URL = MainActivity.serverUrl;

    JsonParser jsonParser = new JsonParser();
    MemberDto memberDto = new MemberDto();

    Object type;

    public ServerCommunicationForMember(Map<String, Object> postData){
        System.out.println("ServerCommunicationForMember");
        this.type = postData.get("type");
        this.postData = new JSONObject(postData);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MemberDto doInBackground(String... params) {
        System.out.println("type :: " + type);
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

                System.out.println("type :: " + type);
                System.out.println("서버로부터의 응답");
                System.out.println(response);
                // 로컬 회원가입시
                // 아이디 중복확인 후 서버에서 되돌아오는 JSON (이메일 중복시 '{}')

                if(type.equals("local")) {
                    if (response.equals("{}")) {
                        return memberDto;
                    }
                }

                if(type.equals("kakao")){
                    if (!(response.equals("")) || !(response.equals("{}"))) {
                        memberDto = jsonParser.JsonParserForKakao(response);

                        // 카카오로 로그인 했을 때의 분기문
                        // 신규회원일 때
                        if (memberDto.getType().equals("kakao") && memberDto.getEmail().equals("")) {
                            System.out.println("신규가입");
                        }
                    }
                } else if(type.equals("local")){
                        System.out.println("local로그인 JSONParser");
                        memberDto = jsonParser.JsonParserForLocal(response);

                }
            } else {

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return memberDto;
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
    protected void onPostExecute(MemberDto s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(MemberDto s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}