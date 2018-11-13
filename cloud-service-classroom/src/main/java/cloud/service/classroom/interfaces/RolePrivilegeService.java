package cloud.service.classroom.interfaces;

import java.util.List;

import com.github.pagehelper.Page;

import cloud.entity.classroom.every.RolePrivilege;


public interface RolePrivilegeService
{
	int insert(RolePrivilege record);
	
	List<RolePrivilege> selectAll();
	
	boolean delete(List<Integer> list);
	
	List<RolePrivilege> selectPiece(Integer roleid);
	
}