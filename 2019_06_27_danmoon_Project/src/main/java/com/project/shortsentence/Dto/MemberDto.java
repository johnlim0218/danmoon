package com.project.shortsentence.Dto;

public class MemberDto {
	String mem_idx;
	String mem_email;
	String mem_password;
	String mem_nickname;
	String mem_type;
	String mem_regdate;
	String mem_update;
	
	public String getIdx() {
		return mem_idx;
	}
	public void setIdx(String idx) {
		this.mem_idx = idx;
	}
	public String getEmail() {
		return mem_email;
	}
	public void setEmail(String email) {
		this.mem_email = email;
	}
	public String getPassword() {
		return mem_password;
	}
	public void setPassword(String password) {
		this.mem_password = password;
	}
	public String getNickname() {
		return mem_nickname;
	}
	public void setNickname(String nickName) {
		this.mem_nickname = nickName;
	}
	public String getRegdate() {
		return mem_regdate;
	}
	public void setRegdate(String regdate) {
		this.mem_regdate = regdate;
	}
	public String getType() {
		return mem_type;
	}
	public void setType(String mem_type) {
		this.mem_type = mem_type;
	}
	public String getUpdate() {
		return mem_update;
	}
	public void setUpdate(String mem_update) {
		this.mem_update = mem_update;
	}
	
}
