package com.project.shortsentence.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.shortsentence.Dao.MemberDao;
import com.project.shortsentence.Dto.MemberDto;
 
@Service
public class KakaoMemberService {
    	
		@Autowired
		private SqlSessionTemplate memberSqlSession;
		@Autowired
		MemberDao memberDao;
		@Autowired
		MemberService memberService;
		
	
    public Map<String, Object> checkAlreadyJoinIn (Map<String, Object> kakaoUserData) {
        
    	System.out.println("KaKaoMemberService checkAlreadyJoinIn");
    	
    	Map<String, Object> kakaoLoginData = new HashMap<>();
    	
    	memberDao = memberSqlSession.getMapper(MemberDao.class);
    	
    	
    	System.out.println(kakaoUserData.get("email"));
    	System.out.println(kakaoUserData.get("type"));
    	
    	MemberDto memberDto = memberDao.dupcheckEmail(kakaoUserData);
    	
    	
    	if(memberDto == null) {
    		System.out.println("신규 회원");
    		
    		kakaoLoginData.put("idx", "");
    		kakaoLoginData.put("email", "");
    		kakaoLoginData.put("nickname", "");
    		kakaoLoginData.put("type", "kakao");
    		
    	} else {
    		System.out.println("기존 회원");
    		
    		kakaoLoginData.put("idx", memberDto.getIdx());
    		kakaoLoginData.put("email", memberDto.getEmail());
    		kakaoLoginData.put("nickname", memberDto.getNickname());
    		kakaoLoginData.put("type", memberDto.getType());
    		
    	}
    	
        return kakaoLoginData;
    }
    
   
}