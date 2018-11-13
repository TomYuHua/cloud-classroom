package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.dao.cluster.TeacherDao;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_teacher.Teacher;
import com.singFly.cloud_examination_user.User;

@Service("TeacherUserImpl")
public class TeacherServiceImpl implements UserService {

	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean operateUser(UserVo user) {
		// TODO Auto-generated method stub
		try{
		   int type=user.getOperationType();
		      switch (type) {
		      case 1:
		   Teacher teacher=teacherDao.addTeacher(user.getTeacher());
		  	user.setRelationId(teacher.getId());
			userDao.addUser(user);
			break;

		case 2:
			boolean outCome=teacherDao.updateTeacher(user.getTeacher());
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
	
		try {
			 boolean outCome=userDao.deleteUser(id);
			   if(outCome){
			   teacherDao.deleteTeacher(rel);
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
		return teacherDao.getUser(id);
	}

}
