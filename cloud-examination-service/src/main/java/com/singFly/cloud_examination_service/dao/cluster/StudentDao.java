package com.singFly.cloud_examination_service.dao.cluster;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_student.Student;


public interface StudentDao {
  
	
	
	Student addStudent(Student student);
	
	boolean updateStudent(Student student);
	
	boolean deleteStudent(Integer id);
	
    UserVo getUser(Integer id);
    


}
