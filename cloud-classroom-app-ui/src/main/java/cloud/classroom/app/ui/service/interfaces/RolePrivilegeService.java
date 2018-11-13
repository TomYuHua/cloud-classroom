package cloud.classroom.app.ui.service.interfaces;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cloud.entity.classroom.every.RolePrivilege;

public interface RolePrivilegeService
{
	int insert(RolePrivilege record);
	
	List<RolePrivilege> selectAll();
	
	boolean delete(List<Integer> list);
	
	List<RolePrivilege> selectPiece(Integer roleid);
}
