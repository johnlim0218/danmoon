package com.project.shortsentence.Dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SubscribeDao {
	
	public int insertSubscribe(Map<String, Object> subscribeData);
	
	public int deleteSubscribe(Map<String, Object> subscribeData);
	
	public List<Map<String, Object>> selectSubsscibeList(int s_memidx);

}
