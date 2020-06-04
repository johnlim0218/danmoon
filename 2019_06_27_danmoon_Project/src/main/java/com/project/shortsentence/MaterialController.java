package com.project.shortsentence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.shortsentence.Dto.MaterialDto;
import com.project.shortsentence.Service.MaterialService;

@Controller
public class MaterialController {

	@Autowired
	private MaterialService materialService;
	
	@RequestMapping(value = "/getMaterial")
	@ResponseBody
	public Map<String, Object> selectAMaterial(){
		
		Map<String, Object> materialData = new HashMap<>();
		MaterialDto materialDto = materialService.selectAMaterialService();
		
		materialData.put("material_idx_pk", materialDto.getMaterial_idx_pk());
		materialData.put("material_title", materialDto.getMaterial_title());
		
		return materialData;
	}
	
}
