package com.project.shortsentence;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.shortsentence.Service.SubscribeService;

@Controller
public class SubscribeController {
	
	@Autowired
	private SubscribeService subscribeService;
	
	@RequestMapping(value = "/insertSubscribe/{s_memidx}&{s_subsmemidx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void insertSubscribe(@PathVariable("s_memidx")int s_memidx, @PathVariable("s_subsmemidx")int s_subsmemidx) {
		
		int resultCnt = subscribeService.insertSubscribeService(s_memidx, s_subsmemidx);
		
		if(resultCnt == 0) {
			System.out.println("저장실패");
		} else if(resultCnt == 1) {
			System.out.println("저장성공");
		}
		
	}
	
	@RequestMapping(value = "/deleteSubscribe/{s_memidx}&{s_subsmemidx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void deleteSubscribe(@PathVariable("s_memidx")int s_memidx, @PathVariable("s_subsmemidx")int s_subsmemidx) {
		
		int resultCnt = subscribeService.deleteSubscribeService(s_memidx, s_subsmemidx);
		
		if(resultCnt == 0) {
			System.out.println("삭제실패");
		} else if(resultCnt == 1) {
			System.out.println("삭제성공");
		}
		
	}
	
	@RequestMapping(value = "/getSubscribeList/{s_memidx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> selectSubscribeList(@PathVariable("s_memidx")int s_memidx){
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		
		try {
			entity = new ResponseEntity<>(subscribeService.selectSubscribeListService(s_memidx), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

}
