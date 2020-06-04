package com.project.shortsentence.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shortsentence.Dao.PostDao;

@Service
public class PostService {
	
	@Autowired
	private SqlSessionTemplate postSqlSession;
	@Autowired
	private PostDao postDao;
	
	// ½Å±Ô ±Û »ðÀÔ Service
	public int insertPostService(Map<String, Object> postData) {
		
		int resultCnt = 0;
		
		postDao = postSqlSession.getMapper(PostDao.class);
		resultCnt = postDao.insertNewPost(postData);
		
		return resultCnt;
	}
	
	public List<Map<String, Object>> selectPostlist(int material_idx) {
		postDao = postSqlSession.getMapper(PostDao.class);
		
		List<Map<String, Object>> resultPostlist = postDao.selectPostList(material_idx);
		
		System.out.println(resultPostlist);
		
		return resultPostlist;
	}
	
	public List<Map<String, Object>> selectUserPostlist(int mem_idx, int user_idx){
		
		Map<String, Object> postData = new HashMap<>();
		postData.put("mem_idx", mem_idx);
		postData.put("user_idx", user_idx);		
		
		postDao = postSqlSession.getMapper(PostDao.class);
		
		List<Map<String, Object>> resultPostlist = postDao.selectUserPostlist(postData);
		
		System.out.println(resultPostlist);
		
		return resultPostlist;
	}
	
	public Map<String, Object> selectUserPost(int p_idx){
		postDao = postSqlSession.getMapper(PostDao.class);
		
		Map<String, Object> resultMyPost = postDao.selectUserPost(p_idx);
		
		System.out.println(resultMyPost);
		
		return resultMyPost;
	}
	
	public List<Map<String, Object>> selectMyPostList(int mem_idx){
		
		postDao = postSqlSession.getMapper(PostDao.class);
		
		List<Map<String, Object>> resultPostlist = postDao.selectMyPostList(mem_idx);
		
		System.out.println(resultPostlist);
		
		return resultPostlist;
		
		
	}
	
	public int updateMyPost(Map<String, Object> postData) {
		int resultCnt = 0;
		
		postDao = postSqlSession.getMapper(PostDao.class);
		
		resultCnt = postDao.updateMyPost(postData);
		
		return resultCnt;
	}
	
	public int deleteMyPost(int p_idx) {
		int resultCnt = 0;
		
		postDao = postSqlSession.getMapper(PostDao.class);
		
		resultCnt = postDao.deleteMyPost(p_idx); 
		
		return resultCnt;
	}
}
