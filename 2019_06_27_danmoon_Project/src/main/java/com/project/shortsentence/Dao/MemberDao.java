package com.project.shortsentence.Dao;

import java.util.List;
import java.util.Map;

import com.project.shortsentence.Dto.MemberDto;

public interface MemberDao {
	public MemberDto selectAMem(Map<String, Object> userData);
	
	// �α���
	public MemberDto loginMem(Map<String, Object> userData);
	
	// ȸ������
	public int joinMembership(Map<String, Object> userData);
	
	// �̸��� �ߺ�Ȯ��, īī�� �������� �α��ν� ���Ե� �̸������� Ȯ��
	public MemberDto dupcheckEmail(Map<String, Object> userData);
	
	// �г��� �ߺ�Ȯ��
	public MemberDto dupcheckNickname(Map<String, Object> userData);
	
	// ȸ���˻�
	public List<Map<String, Object>> searchMem(String searchKeyWord);
	
}
