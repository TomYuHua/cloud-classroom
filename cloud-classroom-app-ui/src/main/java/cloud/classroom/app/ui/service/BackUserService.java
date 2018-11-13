package cloud.classroom.app.ui.service;

import java.util.List;

import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.User;

public interface BackUserService {
	
	

	public boolean auditUser(String items,String type);
	
	
	public boolean makeRole(Integer id,String type);
	
	
	public Integer selectTypes(String userName);
	
	public List<ResourcesVo> getCollections(Integer userId);
    
	public boolean addUser(User user);
	
	public User getUserById(Integer id);
	
	public boolean bindUser(User user);
	
	public boolean changeUserRole(String items,Integer userRoleId,Integer createUserId);
	
	
	
}
