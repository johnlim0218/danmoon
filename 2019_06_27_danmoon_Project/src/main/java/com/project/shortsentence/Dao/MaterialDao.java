package com.project.shortsentence.Dao;

import com.project.shortsentence.Dto.MaterialDto;

public interface MaterialDao {
	
	public MaterialDto selectAMaterialForBeforeMorning();
	
	public MaterialDto selectAMaterialForMorning();
	
	public MaterialDto selectAMaterialForAfternoon();
}
