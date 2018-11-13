package cloud.service.classroom.interfaces;

import java.util.List;

import com.github.pagehelper.Page;

import cloud.entity.classroom.every.UserRole;

public interface UserRoleService
{
	int insert(UserRole record);
	
	boolean delete(List<Integer> list);
	
	List<UserRole> selectAll();
}