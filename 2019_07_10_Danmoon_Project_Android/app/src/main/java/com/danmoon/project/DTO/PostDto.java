package com.danmoon.project.DTO;

public class PostDto {

    int p_idx_pk;
    int p_mem_idx_fk;
    int p_material_idx_fk;
    String p_material;
    String p_content;
    String p_regdate;
    String p_update;
    String p_public;
    int p_likeit;
    int p_hit;

    // Subscribe 속성
     int s_idx_pk;
     int s_memidx_fk;
     int s_subsmemidx_fk;

    // SQLite 속성
    int tempSave_idx;
    String onDB;  // 임시저장한 글이 데이터베이스에 저장되어있는가 (SQLite 속성)
    String mem_nickname;

    public int getP_idx_pk() {
        return p_idx_pk;
    }
    public void setP_idx_pk(int p_idx_pk) {
        this.p_idx_pk = p_idx_pk;
    }
    public int getP_mem_idx_fk() {
        return p_mem_idx_fk;
    }
    public void setP_mem_idx_fk(int p_mem_idx_fk) {
        this.p_mem_idx_fk = p_mem_idx_fk;
    }

    public int getP_material_idx_fk() { return p_material_idx_fk; }
    public void setP_material_idx_fk(int p_material_idx_fk) {this.p_material_idx_fk = p_material_idx_fk;}

    public String getP_material() {
        return p_material;
    }
    public void setP_material(String p_material) {
        this.p_material = p_material;
    }
    public String getP_content() {
        return p_content;
    }
    public void setP_content(String p_content) {
        this.p_content = p_content;
    }
    public String getP_regdate() {
        return p_regdate;
    }
    public void setP_regdate(String p_regdate) {
        this.p_regdate = p_regdate;
    }
    public String getP_update() {
        return p_update;
    }
    public void setP_update(String p_update) {
        this.p_update = p_update;
    }
    public String getP_public() {
        return p_public;
    }
    public void setP_public(String p_public) {
        this.p_public = p_public;
    }

    public int getP_likeit() {
        return p_likeit;
    }

    public void setP_likeit(int p_likeit) {
        this.p_likeit = p_likeit;
    }

    public int getP_hit() {
        return p_hit;
    }
    public void setP_hit(int p_hit) {
        this.p_hit = p_hit;
    }

    public int getS_idx_pk() {
        return s_idx_pk;
    }

    public void setS_idx_pk(int s_idx_pk) {
        this.s_idx_pk = s_idx_pk;
    }

    public int getS_memidx_fk() {
        return s_memidx_fk;
    }

    public void setS_memidx_fk(int s_memidx_fk) {
        this.s_memidx_fk = s_memidx_fk;
    }

    public int getS_subsmemidx_fk() {
        return s_subsmemidx_fk;
    }

    public void setS_subsmemidx_fk(int s_subsmemidx_fk) {
        this.s_subsmemidx_fk = s_subsmemidx_fk;
    }

    public int getTempSave_idx() {
        return tempSave_idx;
    }

    public void setTempSave_idx(int tempSave_idx) {
        this.tempSave_idx = tempSave_idx;
    }

    public String getOnDB() {
        return onDB;
    }

    public void setOnDB(String onDB) {
        this.onDB = onDB;
    }

    public String getMem_nickname() {
        return mem_nickname;
    }

    public void setMem_nickname(String mem_nickname) {
        this.mem_nickname = mem_nickname;
    }
}
