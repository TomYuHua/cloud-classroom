package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.every.UserRole;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRoleDao
{
	Integer insert(UserRole record);
	
	int delete(List<Integer> list);
	
	List<UserRole> selectAll();
	
}