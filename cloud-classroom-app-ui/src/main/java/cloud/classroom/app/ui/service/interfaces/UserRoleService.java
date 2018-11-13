package cloud.classroom.app.ui.service.interfaces;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cloud.entity.classroom.every.UserRole;

public interface UserRoleService
{
	int insert(UserRole record);
	
	boolean delete(List<Integer> list);
	
	List<UserRole> selectAll();
}