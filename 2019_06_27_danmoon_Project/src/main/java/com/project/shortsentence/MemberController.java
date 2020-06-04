package com.project.shortsentence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.shortsentence.Dto.MemberDto;
import com.project.shortsentence.Service.KakaoMemberService;
import com.project.shortsentence.Service.MemberService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private KakaoMemberService kakaoMemberService;
	
	@RequestMapping("/")
	public String enterIndex() {
		
		System.out.println("login");
		
		return "login";
	}
	
	@RequestMapping(value = "/join", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> memberJoinPass(@RequestBody Map<String, Object> userData) {

		Map<String, Object> joinData = new HashMap<>();
		
		boolean availableJoining = memberService.joinMembershipService(userData);
		
		if(availableJoining == true) {
			MemberDto memberDto = memberService.selectMemberService(userData);
			joinData.put("idx", memberDto.getIdx());
			joinData.put("email", memberDto.getEmail());
			joinData.put("nickname", memberDto.getNickname());
			joinData.put("regdate", memberDto.getRegdate());
			joinData.put("type", memberDto.getType());
		}
		
		return joinData;
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> memberLogin(@RequestBody Map<String, Object> userData) {
		
		Map<String, Object> loginData = new HashMap<>();
		
		MemberDto memberDto = memberService.loginService(userData);
		
		loginData.put("idx", memberDto.getIdx());
		loginData.put("email", memberDto.getEmail());
		loginData.put("nickname", memberDto.getNickname());
		loginData.put("regdate", memberDto.getRegdate());
		loginData.put("type", memberDto.getType());
		
		return loginData;
		}
		
//	@RequestMapping(value = "/kakaologinpage")
//	public String kakaoLoginPage() {
//		return "kakaologin";
//	}
	
	@RequestMapping(value = "/kakaologin", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> kakaoMemberLogin(@RequestBody Map<String, Object> kakaoUserData) {
		System.out.println("kakaoMemberLogin");
		Map<String, Object> kakaoLoginData = kakaoMemberService.checkAlreadyJoinIn(kakaoUserData);
	    
		if(kakaoLoginData.get("email").equals("")) {
			System.out.println("신규 회원");
		} else {
			System.out.println("기존 회원");
		}
	    System.out.println("email :: " + kakaoLoginData.get("email"));
	    
	    return kakaoLoginData;
	
	}
	
	@RequestMapping(value = "/getSearchResult/mem/{searchKeyWord}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> searchMem(@PathVariable("searchKeyWord") String searchKeyWord){
		System.out.println(searchKeyWord);
		
		ResponseEntity<List<Map<String, Object>>> entity = null;
		
		try {
			entity = new ResponseEntity<>(memberService.searchMemService(searchKeyWord), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
}
