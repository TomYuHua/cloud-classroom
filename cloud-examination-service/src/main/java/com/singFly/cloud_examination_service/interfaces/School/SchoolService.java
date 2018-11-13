package com.singFly.cloud_examination_service.interfaces.School;

import java.util.List;

import com.singFly.cloud_examination_DTO.SchoolVo;
import com.singFly.cloud_examination_school.School;

public interface SchoolService {

	
	List<School> getSchoolists(School school);
	
	boolean operateSchool(SchoolVo school);
	
	boolean deleteSchool(List<Integer> ids);
	
	School getSchoolById(int id);

}
