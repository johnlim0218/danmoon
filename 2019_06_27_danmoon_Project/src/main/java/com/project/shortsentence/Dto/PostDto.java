package com.project.shortsentence.Dto;

public class PostDto {
	int p_idx_pk;
	int p_mem_idx_fk;
	String p_material;
	String p_content;
	String p_regdate;
	String p_update;
	String p_public;
	int p_hit;
	
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
	public int getP_hit() {
		return p_hit;
	}
	public void setP_hit(int p_hit) {
		this.p_hit = p_hit;
	}
}
