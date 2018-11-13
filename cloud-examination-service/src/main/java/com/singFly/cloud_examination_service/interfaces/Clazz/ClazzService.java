package com.singFly.cloud_examination_service.interfaces.Clazz;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_clazz.Clazz;

public interface ClazzService {
	
	List<ClazzVo> getClazzLists(Integer schoolId,String clazzName);
	
	boolean addClazz(Clazz clazz);
	
	boolean deleteClazz(List<Integer> ids);
	
	boolean updateClazz(Clazz clazz);
	
	ClazzVo getClazzById(int id);

}
