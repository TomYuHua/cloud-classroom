package com.singFly.cloud_examination_service.interfaces.User;

import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_user.User;

public interface UserOperateService {
	
	public User getUserByUserName(String userName);
	
    public boolean resetPassWord(UserVo user);


}
