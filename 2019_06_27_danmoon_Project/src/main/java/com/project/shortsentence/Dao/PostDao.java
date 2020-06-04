package com.project.shortsentence.Dao;

import java.util.List;
import java.util.Map;

public interface PostDao {
	
	public int insertNewPost(Map<String, Object> postData);
	
	public List<Map<String, Object>> selectPostList(int material_idx);
	
	public List<Map<String, Object>> selectUserPostlist(Map<String, Object> postData);
	
	public Map<String, Object> selectUserPost(int p_idx);
	
	public List<Map<String, Object>> selectMyPostList(int mem_idx);
	
	public int updateMyPost(Map<String, Object> postData);
	
	public int deleteMyPost(int p_idx);
}
