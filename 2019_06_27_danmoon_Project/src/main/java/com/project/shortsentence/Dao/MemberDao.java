package com.project.shortsentence.Dao;

import java.util.List;
import java.util.Map;

import com.project.shortsentence.Dto.MemberDto;

public interface MemberDao {
	public MemberDto selectAMem(Map<String, Object> userData);
	
	// 로그인
	public MemberDto loginMem(Map<String, Object> userData);
	
	// 회원가입
	public int joinMembership(Map<String, Object> userData);
	
	// 이메일 중복확인, 카카오 계정으로 로그인시 가입된 이메일인지 확인
	public MemberDto dupcheckEmail(Map<String, Object> userData);
	
	// 닉네임 중복확인
	public MemberDto dupcheckNickname(Map<String, Object> userData);
	
	// 회원검색
	public List<Map<String, Object>> searchMem(String searchKeyWord);
	
}
