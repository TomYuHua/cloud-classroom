package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_user.User;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean operateUser(UserVo user) {
		// TODO Auto-generated method stub
		int type=user.getOperationType();
		switch (type){
		case 1:
		  	userDao.addUser(user);
			break;

		   case 2:
		 	userDao.updateUser(user);
		  }
		return true;
	}

	@Override
	public boolean deleteUser(Integer id, Integer rel) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(id);
	}

	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getUser(id);
	}
	

}
