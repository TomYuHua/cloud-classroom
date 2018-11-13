package com.singFly.cloud_examination_service.interfaces.User;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_user.User;

public  interface UserService {
	
	public boolean operateUser(UserVo user);
	
	public boolean deleteUser(Integer id,Integer rel);
	
	public User getUser(Integer id);
	
	

	
	

}
