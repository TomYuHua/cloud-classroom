package cloud.classroom.app.ui.service;

import cloud.entity.classroom.every.User;

public interface BackStudentService {
	
	
	public boolean insertStudent(User user);
	
    public boolean updateStudent(User user);
    
    public boolean deleteStudent(Integer id);

}
