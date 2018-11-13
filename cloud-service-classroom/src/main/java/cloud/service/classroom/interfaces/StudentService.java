package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.every.User;
import cloud.entity.classroom.student.Student;

public interface StudentService {

	
 
	
	  public User getStudent(Integer id) throws Exception;
		
	  public boolean insertStudent(User user);
		
	  public boolean deleteStudent(Integer id);
		
//	  public boolean updateStudent(User user);

	  public List<User> getByPage(Integer page,Integer rows,User user);
}
