package com.project.shortsentence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.shortsentence.Service.PostService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/insertPost", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> insertPost(@RequestBody Map<String, Object> postData) {
		System.out.println(postData);
		Map<String, Object> insertPostData = new HashMap<>();
		
		int resultCnt = postService.insertPostService(postData);
		if(resultCnt == 0) {
			System.out.println("�������");
		} else if(resultCnt == 1) {
			System.out.println("���强��");
		}
		
		return insertPostData;
	}
	
	@RequestMapping(value = "/getPostlist/{material_idx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> selectPostlist(@PathVariable("material_idx") int material_idx) {
		System.out.println(material_idx);
		
		 ResponseEntity<List<Map<String, Object>>> entity = null;
		 
        try{
        	entity = new ResponseEntity<>(postService.selectPostlist(material_idx), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
		
		return entity;
	}
	
	@RequestMapping(value = "/getUserPostlist/{mem_idx}&{user_idx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> selectUserPostlist(@PathVariable("mem_idx")int mem_idx, @PathVariable("user_idx")int user_idx){
		System.out.println(mem_idx);
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		 
        try{
        	entity = new ResponseEntity<>(postService.selectUserPostlist(mem_idx, user_idx), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
		
		return entity;
	}
	
	@RequestMapping(value = "/getUserPost/{p_idx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> selectUserPost(@PathVariable("p_idx")int p_idx){
		System.out.println("�۹�ȣ : " + p_idx);
		
		Map<String, Object> resultMyPost = postService.selectUserPost(p_idx);
		
		return resultMyPost;
	}
	
	@RequestMapping(value = "/getMyPostList/{mem_idx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> selectMyPostList(@PathVariable("mem_idx")int mem_idx){
		System.out.println(mem_idx);
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		 
        try{
        	entity = new ResponseEntity<>(postService.selectMyPostList(mem_idx), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            entity = new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
		
		return entity;
	}
	
	@RequestMapping(value ="/updateMyPost", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void updateMyPost(@RequestBody Map<String, Object> postData) {
		System.out.println(postData.toString());
		
		int resultCnt = postService.updateMyPost(postData);
		
		if(resultCnt == 0) {
			System.out.println("��������");
		} else if(resultCnt == 1) {
			System.out.println("��������");
		}
	}
	
	@RequestMapping(value="/deleteMyPost/{p_idx}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void deleteMyPost(@PathVariable("p_idx")int p_idx) {
		System.out.println("���� �۹�ȣ : " + p_idx);
		
		int resultCnt = postService.deleteMyPost(p_idx);
		
		if(resultCnt == 0) {
			System.out.println("��������");
		} else if(resultCnt == 1) {
			System.out.println("��������");
		}
	}
}
