package com.project.shortsentence.Service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shortsentence.Dao.MemberDao;
import com.project.shortsentence.Dto.MemberDto;

@Service
public class MemberService {
	
	@Autowired
	private SqlSessionTemplate memberSqlSession;
	@Autowired
	private MemberDao memberDao;
	
	// 로그인 Service
	public MemberDto loginService(Map<String, Object> userData) {
		
		System.out.println("login_Service");
		
		memberDao = memberSqlSession.getMapper(MemberDao.class);
		
		MemberDto memberDto = memberDao.loginMem(userData);
		
		return memberDto;
	}
	
	// 회원가입 Service
	public boolean joinMembershipService(Map<String, Object> userData) {
		
		int resultCnt = 0;
		boolean availableJoining = true;
		
		System.out.println("joinMembership_Service");
		
		memberDao = memberSqlSession.getMapper(MemberDao.class);
		
		// local 회원가입일 경우의 이메일 중복 확인
		// 중복된 아이디라면 availableJoining이 false가 된다.
		if(userData.get("type").equals("local")){
			MemberDto memberDto = memberDao.dupcheckEmail(userData);
			if(memberDto != null) {
				System.out.println("dupcheckEmail : 아이디 중복됨");
				availableJoining = false;
				return availableJoining;
			}
		
			// 기타 플랫폼(카카오 등)을 통해 회원을 가입하는 경우에도 계정 중복확인을 해준다.
		} else {
			MemberDto memberDto = memberDao.dupcheckEmail(userData);
			if(memberDto != null) {
				System.out.println("dupcheckEmail : 이미 가입된 소셜 아이디");
				availableJoining = false;
				return availableJoining;
			}
		}
		
		resultCnt = memberDao.joinMembership(userData);
		if(resultCnt != 1) {
			availableJoining = false;
		}
		
		return availableJoining;
	}
	
	// 
	public MemberDto selectMemberService(Map<String, Object> userData) {
		
		System.out.println("selectMember_Service");
		
		memberDao = memberSqlSession.getMapper(MemberDao.class);
		
		MemberDto memberDto = memberDao.selectAMem(userData);
		 
		return memberDto;
	}
	
	public List<Map<String, Object>> searchMemService(String searchKeyWord) {
		
		System.out.println("searchMem_Service");
		
		memberDao = memberSqlSession.getMapper(MemberDao.class);
		
		List<Map<String, Object>> resultSearchMem = memberDao.searchMem(searchKeyWord);
		
		return resultSearchMem;
	}
}
