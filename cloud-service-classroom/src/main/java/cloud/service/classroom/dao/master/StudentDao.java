package cloud.service.classroom.dao.master;

import java.util.List;

import cloud.entity.classroom.student.Student;

public interface StudentDao
{

	int insert(Student student);

	List<Student> getBypage();
	
	int deleteStudent(Integer id);
}