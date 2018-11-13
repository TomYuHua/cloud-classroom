package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;

public interface TeacherService2 {
	
	public User getTeacher(Integer id) throws Exception;
	
	public boolean insertTeacher(User user);
	
	public boolean deleteTeacher(Integer id);
	
//	public boolean updateTeacher(User user);
	
	public List<User> getByPage(int page,int rows,User user);

}
