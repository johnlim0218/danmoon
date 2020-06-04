package com.project.shortsentence.Dto;


public class SubscribeDto {

    int s_idx;
    int s_memidx;
    int s_subsmemidx;
    int s_regdate;

    String mem_nickname;
    int mem_postcount;

    public int getS_idx() {
        return s_idx;
    }

    public void setS_idx(int s_idx) {
        this.s_idx = s_idx;
    }

    public int getS_memidx() {
        return s_memidx;
    }

    public void setS_memidx(int s_memidx) {
        this.s_memidx = s_memidx;
    }

    public int getS_subsmemidx() {
        return s_subsmemidx;
    }

    public void setS_subsmemidx(int s_subsmemidx) {
        this.s_subsmemidx = s_subsmemidx;
    }

    public int getS_regdate() {
        return s_regdate;
    }

    public void setS_regdate(int s_regdate) {
        this.s_regdate = s_regdate;
    }

    public String getMem_nickname() {
        return mem_nickname;
    }

    public void setMem_nickname(String mem_nickname) {
        this.mem_nickname = mem_nickname;
    }

    public int getMem_postcount() {
        return mem_postcount;
    }

    public void setMem_postcount(int mem_postcount) {
        this.mem_postcount = mem_postcount;
    }
}