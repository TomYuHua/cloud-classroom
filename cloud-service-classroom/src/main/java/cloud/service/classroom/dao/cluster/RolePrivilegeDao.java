package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.every.RolePrivilege;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RolePrivilegeDao
{
	Integer insert(RolePrivilege record);
	
	int delete(List<Integer> list);
	
	List<RolePrivilege> selectAll();
	
	List<RolePrivilege> selectPiece(@Param("roleid") Integer roleid);
	
}