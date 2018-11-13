package cloud.service.classroom.dao.cluster;

import java.util.List;

import com.github.pagehelper.Page;

import cloud.entity.classroom.every.Student;
import cloud.entity.classroom.every.User;

public interface StudentDao2 {
	void insert(Student student);

	Page<User> getByPage(User user);
	
	User getStudent(Integer id);
	
	int insertStudent(Student student);
	
	int deleteStudent(Integer id);
	
	int updateStudent(Student student);

}
