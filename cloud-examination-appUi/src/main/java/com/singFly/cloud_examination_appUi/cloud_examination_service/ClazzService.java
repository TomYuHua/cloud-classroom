package com.singFly.cloud_examination_appUi.cloud_examination_service;

import java.util.List;

import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_clazz.Clazz;
import com.singFly.cloud_examination_idsBean.IdsBean;

public interface ClazzService {

	
	List<ClazzVo> getClazzLists(Integer schoolId,String clazzName);
	
	boolean addClazz(String schoolName,int schoolId);
		
	boolean deleteClazz(IdsBean idsBean);
	
	boolean updateClazz(Clazz clazz);
	
	ClazzVo getClazzById(int id);
	
}
