package cloud.classroom.app.ui.service;

import cloud.entity.classroom.every.User;

public interface BackTeacherService {

	boolean insertTeacher(User user);
	
	boolean updateTeacher(User user);
	
	boolean deleteTeacher(Integer id);
	
	

}
