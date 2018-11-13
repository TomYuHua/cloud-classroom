package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.dao.cluster.StudentDao;
import com.singFly.cloud_examination_service.dao.cluster.TeacherDao;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_student.Student;
import com.singFly.cloud_examination_teacher.Teacher;
import com.singFly.cloud_examination_user.User;

@Service("StudentUserImpl")
public class StudentServiceImpl implements UserService {

	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean operateUser(UserVo user) {
		// TODO Auto-generated method stub
		try{
			   int type=user.getOperationType();
			      switch (type) {
			      case 1:
			   Student student=studentDao.addStudent(user.getStudent());
			  	user.setRelationId(student.getId());
				userDao.addUser(user);
				break;

			case 2:
				boolean outCome=studentDao.updateStudent(user.getStudent());
				if(outCome){
				userDao.updateUser(user);
				}
			   }
			 }catch(Exception e){
				 e.printStackTrace();
				  return false;
			 }
			return true;
	}

	@Override
	public boolean deleteUser(Integer id, Integer rel) {
		// TODO Auto-generated method stub
		try {
			 boolean outCome=userDao.deleteUser(id);
			   if(outCome){
			   studentDao.deleteStudent(rel);
		    }
		} catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
			   return false;
		}// TODO Auto-generated method stub

		      return true;
	}

	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return studentDao.getUser(id);
	}

}
