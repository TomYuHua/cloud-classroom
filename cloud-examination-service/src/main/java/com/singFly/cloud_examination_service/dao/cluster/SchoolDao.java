package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;


import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_school.School;

public interface SchoolDao {
	
	List<School> getSchoolists(School school);
	
	boolean addSchool(School school);
	
	boolean deleteSchool(List<Integer> ids);
	
	boolean updateSchool(School school);
	
	School getSchoolById(int id);

}
