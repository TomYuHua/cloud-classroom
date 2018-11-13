package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.cloud_examination_service_Util.MD5Util;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_user.User;


@Service("UserServiceImpl")
public class UserServiceImpl implements UserService  {

	@Autowired
	private UserDao userDao;
	

	
	public boolean operateUser(UserVo user){
		int userResult=0;
		int type=user.getOperationType();
		try{
		switch (type) {
		case 0:
			if(null==user.getPassWord()){
	        String pwd = MD5Util.getEncryptedPwd("123456");
			user.setPassWord(pwd);}
	    	userResult=userDao.addUser(user);
			break;

		case 1:
		    userResult=userDao.updateUser(user);
			break;
		}

	    if(userResult>0){
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
	
	public boolean deleteUser(Integer id,Integer relationId){
		return userDao.deleteUser(id);
	}
	
	public User getUser(Integer id){
       return userDao.getUser(id);
	}



}