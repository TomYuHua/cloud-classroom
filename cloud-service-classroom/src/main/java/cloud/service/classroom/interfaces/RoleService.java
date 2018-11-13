package cloud.service.classroom.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.Page;

import cloud.entity.classroom.every.UserRole;
import cloud.entity.classroom.every.Role;

public interface RoleService
{
	int insert(Role record);
	
	List<Role> selectAll();
	
	boolean delete(List<Integer> list);
	
	boolean change(Role role);
}