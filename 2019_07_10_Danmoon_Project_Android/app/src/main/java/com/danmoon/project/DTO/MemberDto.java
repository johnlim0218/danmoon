package com.danmoon.project.DTO;

public class MemberDto {
    int idx;
    String email;
    String password;
    String type;
    String nickname;
    String regdate;
    String update;

    int countpost; // 회원정보 검색시 함께 노출되는 작성 글 개수

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public int getCountpost() {
        return countpost;
    }

    public void setCountpost(int countpost) {
        this.countpost = countpost;
    }
}
