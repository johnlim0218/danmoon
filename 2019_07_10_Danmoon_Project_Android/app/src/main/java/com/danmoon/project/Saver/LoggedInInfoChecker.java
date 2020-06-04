package com.danmoon.project.Saver;

import android.content.Context;
import android.content.SharedPreferences;

import com.danmoon.project.DTO.MemberDto;

public class LoggedInInfoChecker {

    Context context;

    public LoggedInInfoChecker(Context context){

        this.context = context;
    }
    public MemberDto loggedInInfoReader(){
        MemberDto memberDto = new MemberDto();
        SharedPreferences logedInInfo = context.getSharedPreferences("logedininfo", context.MODE_PRIVATE);

        System.out.println(logedInInfo.getString("email", "정보없음"));
        System.out.println(logedInInfo.getString("email", "정보없음"));

        memberDto.setEmail(logedInInfo.getString("email", "정보없음"));
        memberDto.setPassword(logedInInfo.getString("password", "정보없음"));
        memberDto.setNickname(logedInInfo.getString("nickname", "정보없음"));
        memberDto.setType(logedInInfo.getString("type", "정보없음"));
        memberDto.setIdx(Integer.parseInt(logedInInfo.getString("idx", "0")));

        return memberDto;
    }

    public void logedInInfoWriter(MemberDto memberDto){
        System.out.println("logedInInfoWriter 시작");
        SharedPreferences logedInInfo = context.getSharedPreferences("logedininfo", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = logedInInfo.edit();
        editor.putString("email", memberDto.getEmail());
        editor.putString("password", memberDto.getPassword());
        editor.putString("nickname", memberDto.getNickname());
        editor.putString("type", memberDto.getType());
        editor.putString("idx", memberDto.getIdx() + "");
        editor.commit();


        System.out.println(logedInInfo.getString("email", "정보없음"));
        System.out.println(logedInInfo.getString("password", "정보없음"));
        System.out.println(logedInInfo.getString("nickname", "정보없음"));
        System.out.println(logedInInfo.getString("type", "정보없음"));
        System.out.println(logedInInfo.getString("idx", "0"));
        System.out.println("logedInInfoWriter 끝");
    }

    public void loggedInInfoTerminator(){
        SharedPreferences logedInInfo = context.getSharedPreferences("logedininfo", context.MODE_PRIVATE);
        SharedPreferences.Editor terminator = logedInInfo.edit();
        terminator.clear();
        terminator.commit();
    }
}
