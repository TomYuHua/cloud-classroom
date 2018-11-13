package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.every.Role;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

public interface RoleDao
{
	Integer insert(Role record);
	
	int delete(List<Integer> list);
	
	int change(Role role);
	
	
	List<Role> selectAll();
}