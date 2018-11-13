package com.singFly.cloud_examination_service.dao.cluster;

import com.singFly.cloud_examination_DTO.UserVo;





public interface UserDao {
	
	int updateUser(UserVo user);
	
	int addUser(UserVo user);
	
	boolean deleteUser(Integer id);
	
	UserVo getUser(Integer id);
	
	UserVo getUserByUserName(String userName);
	
	boolean resetPassWord(UserVo user);
	
	

}
