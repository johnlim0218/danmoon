package com.project.shortsentence.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shortsentence.Dao.MaterialDao;
import com.project.shortsentence.Dto.MaterialDto;

@Service
public class MaterialService {

	String timeStartBeforeMorning = "000000";
	String timeStartMorning = "065959";
	String timeStartAfternoon = "172959";
	String timeEndOfTheDay = "235959";
	
	@Autowired
	private SqlSessionTemplate materialSqlSession;
	@Autowired
	private MaterialDao materialDao;
	
	private MaterialDto materialDto;
	
	public MaterialDto selectAMaterialService() {
		String time = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String todayDate = dateFormat.format(today);
		
		String dateTimeBeforeMorning = todayDate + timeStartBeforeMorning;
		String dateTimeMorning = todayDate + timeStartMorning;
		String dateTimeAfterNoon = todayDate + timeStartAfternoon;
		String dateTimeEndOfTheDay = todayDate + timeEndOfTheDay;
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		
		try {
			Date dateBeforeMorning = timeFormat.parse(dateTimeBeforeMorning);
			Date dateMorning = timeFormat.parse(dateTimeMorning);
			Date dateAfternoon = timeFormat.parse(dateTimeAfterNoon);
			Date dateEndOfTheDay = timeFormat.parse(dateTimeEndOfTheDay);
			
			
			if(now.getTime() > dateBeforeMorning.getTime() && now.getTime() <= dateMorning.getTime()) {
				time = "새벽";
			} else if(now.getTime() > dateMorning.getTime() && now.getTime() <= dateAfternoon.getTime()) {
				time = "오전";
			} else if(now.getTime() > dateAfternoon.getTime() && now.getTime() <= dateEndOfTheDay.getTime()) {
				time = "오후";
			} 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(time);
		
		materialDao = materialSqlSession.getMapper(MaterialDao.class);
		
		if(time.contentEquals("새벽")) {
			materialDto = materialDao.selectAMaterialForBeforeMorning();
		} else if(time.contentEquals("오전")) {
			materialDto = materialDao.selectAMaterialForMorning();
		} else if(time.contentEquals("오후")) {
			materialDto = materialDao.selectAMaterialForAfternoon();
		}
		
		System.out.println(materialDto.getMaterial_title());
		
		return materialDto;
	}
	
}
