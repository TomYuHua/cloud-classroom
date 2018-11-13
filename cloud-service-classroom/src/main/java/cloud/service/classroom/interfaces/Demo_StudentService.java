package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.every.User;
import cloud.entity.classroom.student.Student;

public interface Demo_StudentService {

	
	
    public User getStudent(Integer id) throws Exception;
	
	public boolean insertStudent(User user);
	
	public boolean deleteStudent(Integer id);
	
	public boolean updateStudent(User user);

	
	public List<User> getByPage(int page,int rows,User user);
//	
//	public Student getStudent(Integer id);
//	
//	public void insertStudent(Student student);
//	
//	public void deleteStudent(Integer id);
//	
//	public void updateStudent(Student student);
//	
//	
//	public List<Student> getByPage(int page,int rows);
}
