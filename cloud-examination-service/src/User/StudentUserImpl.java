package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.cloud_examination_service_Util.MD5Util;
import com.singFly.cloud_examination_service.dao.cluster.StudentDao;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_user.User;

@Service("StudentUserImpl")
public class StudentUserImpl implements UserService {
	

	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private UserDao userDao;
	
	public boolean operateUser(UserVo user) {
		int studentResult=0;
		int userResult=0;
		int type=user.getOperationType();
		try{
		switch (type) {
		case 0:
	    	studentResult=studentDao.addStudent(user.getStudent());
			String pwd = MD5Util.getEncryptedPwd("123456");
			user.setPassWord(pwd);
	    	userResult=userDao.addUser(user);
			break;

		case 1:
	     	studentResult=studentDao.updateStudent(user.getStudent());
		    userResult=userDao.updateUser(user);
			break;
		}

	    if(studentResult>0 && userResult>0){
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
	
	public  boolean deleteUser(Integer id,Integer relationId){
		boolean resultUser=userDao.deleteUser(id);
		boolean resultStudent=studentDao.deleteStudent(relationId);
		if(resultUser && resultStudent){
			return true;
		}else{
			return false;
		}
	}

     public User getUser(Integer id) {	
    	 return studentDao.getUser(id);
	}
}
