package com.singFly.cloud_examination_appUi.cloud_examination_service;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_user.User;

public interface UserService {
	
	public boolean operateUser(UserVo user);
	
	public boolean deleteUsers(String items);
	
	public User getUser(int id,int type);
	
	public User checkExistMail(String email);
	
	public boolean resetPassWord(UserVo user);

}
