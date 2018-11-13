package cloud.classroom.app.ui.service.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.Role;

public interface RoleService
{
	int insert(Role record);
	
	List<Role> selectAll();
	
	boolean delete(List<Integer> list);
	
	boolean change(Role role);
}