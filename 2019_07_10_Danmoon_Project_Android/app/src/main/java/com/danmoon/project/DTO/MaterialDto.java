package com.danmoon.project.DTO;

public class MaterialDto {
    int material_idx_pk;
    String material_title;
    String material_distribute_date;
    String material_distribute_time;

    public int getMaterial_idx_pk() {
        return material_idx_pk;
    }

    public void setMaterial_idx_pk(int material_idx_pk) {
        this.material_idx_pk = material_idx_pk;
    }

    public String getMaterial_title() {
        return material_title;
    }

    public void setMaterial_title(String material_title) {
        this.material_title = material_title;
    }

    public String getMaterial_distribute_date() {
        return material_distribute_date;
    }

    public void setMaterial_distribute_date(String material_distribute_date) {
        this.material_distribute_date = material_distribute_date;
    }

    public String getMaterial_distribute_time() {
        return material_distribute_time;
    }

    public void setMaterial_distribute_time(String material_distribute_time) {
        this.material_distribute_time = material_distribute_time;
    }
}
