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
	
	// �α��� Service
	public MemberDto loginService(Map<String, Object> userData) {
		
		System.out.println("login_Service");
		
		memberDao = memberSqlSession.getMapper(MemberDao.class);
		
		MemberDto memberDto = memberDao.loginMem(userData);
		
		return memberDto;
	}
	
	// ȸ������ Service
	public boolean joinMembershipService(Map<String, Object> userData) {
		
		int resultCnt = 0;
		boolean availableJoining = true;
		
		System.out.println("joinMembership_Service");
		
		memberDao = memberSqlSession.getMapper(MemberDao.class);
		
		// local ȸ�������� ����� �̸��� �ߺ� Ȯ��
		// �ߺ��� ���̵��� availableJoining�� false�� �ȴ�.
		if(userData.get("type").equals("local")){
			MemberDto memberDto = memberDao.dupcheckEmail(userData);
			if(memberDto != null) {
				System.out.println("dupcheckEmail : ���̵� �ߺ���");
				availableJoining = false;
				return availableJoining;
			}
		
			// ��Ÿ �÷���(īī�� ��)�� ���� ȸ���� �����ϴ� ��쿡�� ���� �ߺ�Ȯ���� ���ش�.
		} else {
			MemberDto memberDto = memberDao.dupcheckEmail(userData);
			if(memberDto != null) {
				System.out.println("dupcheckEmail : �̹� ���Ե� �Ҽ� ���̵�");
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
