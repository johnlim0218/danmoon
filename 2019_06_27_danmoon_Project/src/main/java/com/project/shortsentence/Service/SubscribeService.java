package com.project.shortsentence.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shortsentence.Dao.SubscribeDao;

@Service
public class SubscribeService {

	@Autowired
	private SqlSessionTemplate subscribeSqlSession;
	@Autowired
	private SubscribeDao subscribeDao;
	
	// 구독자 등록 Service
	public int insertSubscribeService(int s_memidx, int s_subsmemidx) {
		
		int resultCnt = 0;
		Map<String, Object> subscribeData = new HashMap<>();
		subscribeData.put("s_memidx", s_memidx);
		subscribeData.put("s_subsmemidx", s_subsmemidx);
		
		System.out.println(subscribeData.get("s_memidx"));
		System.out.println(subscribeData.get("s_subsmemidx"));
		
		subscribeDao = subscribeSqlSession.getMapper(SubscribeDao.class);
		resultCnt = subscribeDao.insertSubscribe(subscribeData);
		
		return resultCnt;
	}
	
	// 구독자 삭제 Service
	public int deleteSubscribeService(int s_memidx, int s_subsmemidx) {
		int resultCnt = 0;
		Map<String, Object> subscribeData = new HashMap<>();
		subscribeData.put("s_memidx", s_memidx);
		subscribeData.put("s_subsmemidx", s_subsmemidx);
		
		System.out.println(subscribeData.get("s_memidx"));
		System.out.println(subscribeData.get("s_subsmemidx"));
		
		subscribeDao = subscribeSqlSession.getMapper(SubscribeDao.class);
		resultCnt = subscribeDao.deleteSubscribe(subscribeData);
		
		return resultCnt;
	}
	
	// 구독자 목록 Service
	public List<Map<String, Object>> selectSubscribeListService(int s_memidx){
		subscribeDao = subscribeSqlSession.getMapper(SubscribeDao.class);
		
		List<Map<String, Object>> resultSubscribeList = subscribeDao.selectSubsscibeList(s_memidx);
		
		System.out.println(resultSubscribeList);
		
		return resultSubscribeList;
		
	}
}
