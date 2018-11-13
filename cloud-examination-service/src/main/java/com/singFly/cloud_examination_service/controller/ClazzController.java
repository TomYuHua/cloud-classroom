package com.singFly.cloud_examination_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_clazz.Clazz;
import com.singFly.cloud_examination_service.dao.cluster.ClazzDao;
import com.singFly.cloud_examination_service.interfaces.Clazz.ClazzService;


@RequestMapping("/clazz")
@RestController 
public class ClazzController {
	
	@Autowired
	private ClazzService clazzService;
	
	
	@RequestMapping("/getClazzLists")
	public List<ClazzVo> getClazzLists(Integer schoolId,String clazzName) {
		
		return clazzService.getClazzLists(schoolId, clazzName);
		
	}
	
	@RequestMapping("/addClazz")
	public boolean addClazz(@RequestBody Clazz clazz) {
		
		return clazzService.addClazz(clazz);
		
	}
	
	
	@RequestMapping("/updateClazz")
	public boolean updateClazz(@RequestBody Clazz clazz) {
		
		return clazzService.updateClazz(clazz);
		
	}
	
	
	@RequestMapping("/deleteClazz")
	public boolean deleteClazz(@RequestBody List<Integer> ids) {
		
		return clazzService.deleteClazz(ids);
		
	}
	
	
	
	@RequestMapping("/getClazzById")
	public ClazzVo getClazzById(int id) {
	 return clazzService.getClazzById(id);
		
	}
	


}
