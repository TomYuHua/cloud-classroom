package cloud.service.classroom.interfaces;

import cloud.entity.classroom.every.User;

public interface BackUserService {
	
	
	public boolean addUser(User user);
	
	public boolean bindUser(User user);
	
	public boolean changeUserRole(String items,Integer userRoleId,Integer createUserId);

}
