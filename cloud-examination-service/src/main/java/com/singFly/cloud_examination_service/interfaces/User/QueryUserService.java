package com.singFly.cloud_examination_service.interfaces.User;

import com.singFly.cloud_examination_DTO.QuestionVo;
import com.singFly.cloud_examination_user.User;

public interface QueryUserService {
	
	public User getUserByUserName(String userName);

	public boolean operateQuestion(QuestionVo question);

}
