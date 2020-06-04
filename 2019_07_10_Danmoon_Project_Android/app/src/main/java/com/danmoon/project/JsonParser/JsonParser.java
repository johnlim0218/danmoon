package com.danmoon.project.JsonParser;

import com.danmoon.project.DTO.MaterialDto;
import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.DTO.SubscribeDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public MemberDto JsonParserForKakao(String response){
        System.out.println("JsonParser JsonParserForKakao");
        MemberDto memberDto = new MemberDto();
        try {

            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("idx")) {
                    memberDto.setIdx(jsonObject.getInt("idx"));
                }
                if(jsonObject.has("email")) {
                    memberDto.setEmail(jsonObject.getString("email"));
                }
                if(jsonObject.has("nickname")) {
                    memberDto.setNickname(jsonObject.getString("nickname"));
                }
                if(jsonObject.has("type")) {
                    memberDto.setType(jsonObject.getString("type"));
                }
            } else{
                System.out.println("response는 JSON형태가 아님");
            }
       } catch(JSONException e){
           e.printStackTrace();
       }

        return memberDto;
    }

    public MemberDto JsonParserForLocal(String response){
        System.out.println("JsonParser JsonParserForLocal");
        MemberDto memberDto = new MemberDto();
        try {

            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("idx")) {
                    memberDto.setIdx(jsonObject.getInt("idx"));
                }
                if(jsonObject.has("email")) {
                    memberDto.setEmail(jsonObject.getString("email"));
                }
                if(jsonObject.has("nickname")) {
                    memberDto.setNickname(jsonObject.getString("nickname"));
                }
                if(jsonObject.has("type")) {
                    memberDto.setType(jsonObject.getString("type"));
                }
            } else{
                System.out.println("response는 JSON형태가 아님");
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return memberDto;
    }

    public MaterialDto jsonParserForGetMaterial(String response){
        System.out.println("JsonParser jsonParserForGetMaterial");
        MaterialDto materialDto = new MaterialDto();
        try {

            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("material_idx_pk")) {
                    materialDto.setMaterial_idx_pk(jsonObject.getInt("material_idx_pk"));
                }
                if(jsonObject.has("material_title")) {
                    materialDto.setMaterial_title(jsonObject.getString("material_title"));
                }
            } else{
                System.out.println("response는 JSON형태가 아님");
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return materialDto;
    }

    public ArrayList<PostDto> jsonParserForPostlist(String response){
        System.out.println("JsonParser jsonParserForPostlist");
        ArrayList<PostDto> returnArrForPostList = new ArrayList<>();
        try {

            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONArray jsonArray = new JSONArray(response);

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    PostDto postDto = new PostDto();

                    if(jsonObject.has("p_idx_pk")) {
                        postDto.setP_idx_pk(jsonObject.getInt("p_idx_pk"));
                    }
                    if(jsonObject.has("mem_nickname")) {
                        postDto.setMem_nickname(jsonObject.getString("mem_nickname"));
                    }
                    if(jsonObject.has("p_regdate")) {
                        postDto.setP_regdate(jsonObject.getString("p_regdate"));
                    }
                    if(jsonObject.has("p_likeit")) {
                        postDto.setP_likeit(jsonObject.getInt("p_likeit"));
                    }
                    if(jsonObject.has("p_material")) {
                        postDto.setP_material(jsonObject.getString("p_material"));
                    }
                    if(jsonObject.has("p_content")) {
                        postDto.setP_content(jsonObject.getString("p_content"));
                    }
                    if(jsonObject.has("s_idx_pk")) {
                        postDto.setS_idx_pk(jsonObject.getInt("s_idx_pk"));
                    }
                    if(jsonObject.has("s_memidx_fk")) {
                        postDto.setS_memidx_fk(jsonObject.getInt("s_memidx_fk"));
                    }
                    if(jsonObject.has("s_subsmemidx_fk")) {
                        postDto.setS_subsmemidx_fk(jsonObject.getInt("s_subsmemidx_fk"));
                    }


                    returnArrForPostList.add(postDto);
                }

            } else{
                System.out.println("response는 JSON형태가 아님");
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return returnArrForPostList;
    }

    public PostDto jsonParserForMyPost(String response){
        System.out.println("JsonParser jsonParserForPostlist");
        PostDto postDto = new PostDto();
        try {
            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONObject jsonObject = new JSONObject(response);

                if(jsonObject.has("p_idx_pk")) {
                    postDto.setP_idx_pk(jsonObject.getInt("p_idx_pk"));
                }
                if(jsonObject.has("p_material_idx_fk")){
                    postDto.setP_material_idx_fk(jsonObject.getInt("p_material_idx_fk"));
                }
                if(jsonObject.has("mem_nickname")) {
                    postDto.setMem_nickname(jsonObject.getString("mem_nickname"));
                }
                if(jsonObject.has("p_regdate")) {
                    postDto.setP_regdate(jsonObject.getString("p_regdate"));
                }
                if(jsonObject.has("p_likeit")) {
                    postDto.setP_likeit(jsonObject.getInt("p_likeit"));
                }
                if(jsonObject.has("p_material")) {
                    postDto.setP_material(jsonObject.getString("p_material"));
                }
                if(jsonObject.has("p_content")) {
                    postDto.setP_content(jsonObject.getString("p_content"));
                }
                if(jsonObject.has("p_public"))  {
                    postDto.setP_public(jsonObject.getString("p_public"));
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return postDto;
    }

    public ArrayList<MemberDto> jsonParserForSearchMemResult(String response){
        System.out.println("JsonParser jsonParserForSearchMemResult");
        ArrayList<MemberDto> returnArrForSearchResultMem = new ArrayList<>();
        try {

            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONArray jsonArray = new JSONArray(response);

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    MemberDto memberDto = new MemberDto();

                    if(jsonObject.has("mem_idx")) {
                        memberDto.setIdx(jsonObject.getInt("mem_idx"));
                    }
                    if(jsonObject.has("mem_email")) {
                        memberDto.setEmail(jsonObject.getString("mem_email"));
                    }
                    if(jsonObject.has("mem_nickname")) {
                        memberDto.setNickname(jsonObject.getString("mem_nickname"));
                    }
                    if(jsonObject.has("mem_type")) {
                        memberDto.setType(jsonObject.getString("mem_type"));
                    }
                    if(jsonObject.has("mem_regdate")) {
                        memberDto.setRegdate(jsonObject.getString("mem_regdate"));
                    }
                    if(jsonObject.has("mem_update")) {
                        memberDto.setUpdate(jsonObject.getString("mem_update"));
                    }
                    if(jsonObject.has("countpost")) {
                        memberDto.setCountpost(jsonObject.getInt("countpost"));
                    }

                    returnArrForSearchResultMem.add(memberDto);
                }

            } else{
                System.out.println("response는 JSON형태가 아님");
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
        return returnArrForSearchResultMem;
    }

    public ArrayList<SubscribeDto> jsonParserForSubscribeList(String response){
        System.out.println("JsonParser jsonParserForSubscribeList");
        ArrayList<SubscribeDto> returnArrForSubscribeList = new ArrayList<>();

        try {

            if(isJSONValid(response)) {
                System.out.println("response는 JSON형태가 맞음");
                JSONArray jsonArray = new JSONArray(response);

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SubscribeDto subscribeDto = new SubscribeDto();

                    if(jsonObject.has("mem_idx")) {
                        subscribeDto.setS_memidx(jsonObject.getInt("mem_idx"));
                    }

                    if(jsonObject.has("s_subsmemidx_fk")) {
                        subscribeDto.setS_subsmemidx(jsonObject.getInt("s_subsmemidx_fk"));
                    }

                    if(jsonObject.has("mem_nickname")) {
                        subscribeDto.setMem_nickname(jsonObject.getString("mem_nickname"));
                    }
                    if(jsonObject.has("countpost")) {
                        subscribeDto.setMem_postcount(jsonObject.getInt("countpost"));
                    }


                    returnArrForSubscribeList.add(subscribeDto);
                }

            } else{
                System.out.println("response는 JSON형태가 아님");
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return returnArrForSubscribeList;
    }

    // 서버로 받은 응답이 JSON 형태인지 판별하기위한 메소드
    public boolean isJSONValid(String response) {
        try {
            new JSONObject(response);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(response);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}


