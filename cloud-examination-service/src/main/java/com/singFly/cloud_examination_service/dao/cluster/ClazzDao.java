package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_clazz.Clazz;

public interface ClazzDao{
	
	List<ClazzVo> getClazzLists(@Param("schoolId")Integer schoolId,@Param("clazzName")String clazzName);
	
	boolean addClazz(Clazz clazz);
	
	boolean deleteClazz(List<Integer> ids);
	
	boolean updateClazz(Clazz clazz);
	
	ClazzVo getClazzById(int id);

}
