package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.student.Student;
import cloud.entity.classroom.teacher.Teacher;

public interface Demo_TeacherService {

	public Teacher getTeacher(Integer id);
	
	public void insertTeacher(Teacher teacher);
	
	public void deleteTeacher(Integer id);
	
	public void updateTeacher(Teacher teacher);
	
	public List<Teacher> getByPage(int page,int rows);

}
