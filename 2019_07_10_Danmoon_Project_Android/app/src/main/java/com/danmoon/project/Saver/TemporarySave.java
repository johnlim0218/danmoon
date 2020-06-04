package com.danmoon.project.Saver;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;

import java.util.ArrayList;

public class TemporarySave {

    private final String dbName = "DanmoonLiteDB";
    private final String tableName = "temporarySave";

    SQLiteDatabase sqLiteDatabase = null;

    Context context;

    PostDto postDto;

    public TemporarySave(Context context){
        this.context = context;
    }

//    public void temporarySaver(String content){
//        String fileName = MaterialActivity.fileName;
//        SharedPreferences temporarySaver = context.getSharedPreferences("TemporarySave", context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = temporarySaver.edit();
//        editor.putString(fileName, content);
//        System.out.println(fileName);
//        System.out.println(content);
//        editor.commit();
//
//    }
//
//    public String temporarySaveLoader(){
//        String fileName = MaterialActivity.fileName;
//        String content = "";
//        SharedPreferences temporarySaver = context.getSharedPreferences("TemporarySave", context.MODE_PRIVATE);
//        content = temporarySaver.getString(fileName, "");
//
//        return content;
//    }
//    public Map<String, ?> temporarySaveListLoader(){
//        Map<String, ?> tempSaveListMap = new HashMap<>();
//        SharedPreferences temporarySaver = context.getSharedPreferences("TemporarySave", context.MODE_PRIVATE);
//        tempSaveListMap = temporarySaver.getAll();
//        System.out.println("Test");
//        System.out.println(tempSaveListMap.size());
//        return tempSaveListMap;
//    }
//
//    public void deleteTemporarySaveMoreTenItems(){
//        SharedPreferences temporarySaver = context.getSharedPreferences("TemporarySave", context.MODE_PRIVATE);
//        if(temporarySaver.getAll().size() == 10){
//
//        }
//    }

    public void createTable(){
        sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("create table if not exists " + tableName + " (tempSave_idx integer primary key autoincrement, post_idx int, material_idx int, material varchar(20), content varchar(5000), postpublic varchar(20), ondb varchar(20) not null default 'n', regdate datetime);");
        sqLiteDatabase.close();
    }

    public void insertTempSaveData(int post_idx, int material_id, String material, String content){

            String postPublic = "n";
            String onDB = "n";

        if(MaterialActivity.PUBLIC == MaterialActivity.POST_NOT_PUBLIC){
            postPublic = "n";
        } else if (MaterialActivity.PUBLIC == MaterialActivity.POST_PUBLIC){
            postPublic = "y";
        }

        if(post_idx == 0){
            onDB = "n";
        } else if(post_idx != 0){
            onDB = "y";
        }

        sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("insert into " + tableName + " (post_idx, material_idx, material, content, postpublic, ondb, regdate) values('" + post_idx + "','" + material_id + "','" + material + "', '" + content + "', '" + postPublic + "', '" + onDB + "',datetime('now', 'localtime'));");
        sqLiteDatabase.close();

        selectTempSaveData();
    }

    public ArrayList<PostDto> selectTempSaveData(){

        ArrayList<PostDto> arrForPostDto = new ArrayList<>();

//        sqLiteDatabase.execSQL("select tempSave_idx, material_idx, material, content, regdate from " + tableName + " where temp);

        try{
            sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            Cursor c = sqLiteDatabase.rawQuery("select * from " + tableName, null );
            if(c != null){
                if(c.moveToFirst()){
                    do{
                        int tempSave_idx = c.getInt(c.getColumnIndex("tempSave_idx"));
                        int post_idx = c.getInt(c.getColumnIndex("post_idx"));
                        int materal_idx = c.getInt(c.getColumnIndex("material_idx"));
                        String material = c.getString(c.getColumnIndex("material"));
                        String content = c.getString(c.getColumnIndex("content"));
                        String postPublic = c.getString(c.getColumnIndex("postpublic"));
                        String onDB = c.getString(c.getColumnIndex("ondb"));
                        String regDate = c.getString(c.getColumnIndex("regdate"));

                        postDto = new PostDto();
                        postDto.setTempSave_idx(tempSave_idx);
                        postDto.setP_idx_pk(post_idx);
                        postDto.setP_material_idx_fk(materal_idx);
                        postDto.setP_material(material);
                        postDto.setP_content(content);
                        postDto.setP_public(postPublic);
                        postDto.setOnDB(onDB);
                        postDto.setP_regdate(regDate);

                        arrForPostDto.add(postDto);

                    } while(c.moveToNext());
                }
            }

            sqLiteDatabase.close();
    }catch(SQLiteException se){
            Toast.makeText(context, se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        return arrForPostDto;
    }

    public PostDto selectATempSaveData(int tempSave_idx){
        try{
            sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            Cursor c = sqLiteDatabase.rawQuery("select * from " + tableName + " where tempSave_idx = " + tempSave_idx, null );
        if(c != null){
            if(c.moveToFirst()){
                do{
                    int post_idx = c.getInt(c.getColumnIndex("post_idx"));
                    int materal_idx = c.getInt(c.getColumnIndex("material_idx"));
                    String material = c.getString(c.getColumnIndex("material"));
                    String content = c.getString(c.getColumnIndex("content"));
                    String postPublic = c.getString(c.getColumnIndex("postpublic"));
                    String onDB = c.getString(c.getColumnIndex("ondb"));
                    String regDate = c.getString(c.getColumnIndex("regdate"));

                    postDto = new PostDto();
                    postDto.setTempSave_idx(tempSave_idx);
                    postDto.setP_idx_pk(post_idx);
                    postDto.setP_material_idx_fk(materal_idx);
                    postDto.setP_material(material);
                    postDto.setP_content(content);
                    postDto.setP_public(postPublic);
                    postDto.setOnDB(onDB);
                    postDto.setP_regdate(regDate);

                } while(c.moveToNext());
            }
        }

        sqLiteDatabase.close();
        }catch(SQLiteException se){
            Toast.makeText(context, se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
        return postDto;
    }

    public void updateTempSaveDataAfterInsertDB(int tempSave_idx){
        sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("update " + tableName + " set ondb = 'y' where tempSave_idx = " + tempSave_idx);
        sqLiteDatabase.close();
    }

    public void deleteTempSaveData(){
        int tempSave_idx = 0;
        sqLiteDatabase = context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("delete from " + tableName + " where tempSave_idx = " + tempSave_idx);
        sqLiteDatabase.close();
    }
}
