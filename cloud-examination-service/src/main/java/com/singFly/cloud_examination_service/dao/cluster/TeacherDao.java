package com.singFly.cloud_examination_service.dao.cluster;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_teacher.Teacher;


public interface TeacherDao {
	
	
	Teacher addTeacher(Teacher teacher);
	
	boolean  updateTeacher(Teacher teacher);
	
	boolean deleteTeacher(Integer id);
	
	UserVo getUser(Integer id);

}
