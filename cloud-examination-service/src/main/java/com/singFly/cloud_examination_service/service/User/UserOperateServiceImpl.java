package com.singFly.cloud_examination_service.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.dao.cluster.UserDao;
import com.singFly.cloud_examination_service.interfaces.User.UserOperateService;
import com.singFly.cloud_examination_user.User;


@Service
public class UserOperateServiceImpl implements UserOperateService {

	
	@Autowired
	private UserDao userDao;
	@Override
	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.getUserByUserName(userName) ;
	}

	@Override
	public boolean resetPassWord(UserVo user) {
		// TODO Auto-generated method stub
		return userDao.resetPassWord(user);
	}

}
