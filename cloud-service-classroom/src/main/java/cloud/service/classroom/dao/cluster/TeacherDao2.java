package cloud.service.classroom.dao.cluster;

import java.util.List;

import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;

public interface TeacherDao2 {
	
	void insert(Teacher teacher);

	List<User> getByPage(User user);
	
	User getTeacher(Integer id);
	
	int insertTeacher(Teacher teacher);
	
	int  deleteTeacher(Integer id);
	
	int updateTeacher(Teacher teacher);

}
