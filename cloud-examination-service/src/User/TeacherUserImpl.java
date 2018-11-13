package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.cloud_examination_service_Util.MD5Util;
import com.singFly.cloud_examination_service.dao.cluster.TeacherDao;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_user.User;


@Service("TeacherUserImpl")
public  class TeacherUserImpl implements UserService {

	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private UserDao userDao;
	
	public boolean operateUser(UserVo user) {
		int userResult=0;
		int teacherResult=0;
		int type=user.getOperationType();
	try{
		switch (type) {
		case 0:
			teacherResult=teacherDao.addTeacher(user.getTeacher());
			String pwd = MD5Util.getEncryptedPwd("123456");
			user.setPassWord(pwd);
	    	userResult=userDao.addUser(user);
			break;

		case 1:
			teacherResult=teacherDao.updateTeacher(user.getTeacher());
		    userResult=userDao.updateUser(user);
			break;
		}
		
	    if(teacherResult>0 && userResult>0){
	    	return true;
	    }else{
	        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    	return false;
	     }
	   }catch(Exception e){
		   e.printStackTrace();
		   TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    return false;
	    }
	  }

	public boolean deleteUser(Integer id,Integer re) {
		boolean resultUser=userDao.deleteUser(id);
		boolean resultTeacher=teacherDao.deleteTeacher(id);
		if(resultUser && resultTeacher){
			return true;
		}else{
			return false;
		}
	}
	
    public User getUser(Integer id) {	
   	 return teacherDao.getUser(id);
	}
}
