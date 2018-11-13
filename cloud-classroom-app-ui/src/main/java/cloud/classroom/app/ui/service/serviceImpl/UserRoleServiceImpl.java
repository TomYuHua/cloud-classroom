package cloud.classroom.app.ui.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.service.interfaces.RoleService;
import cloud.classroom.app.ui.service.interfaces.UserRoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.UserRole;

@Service
public class UserRoleServiceImpl implements UserRoleService
{
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String serviceName;

	@HystrixCommand(fallbackMethod = "fallbackuserroleinsert")
	@Override
	public int insert(UserRole record)
	{
		int result = restTemplate.postForObject("http://" + serviceName + "/userrole/insert", record, int.class);

		return result;
	}
	
	public int fallbackuserroleinsert(UserRole record){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return 0;
	}

	@HystrixCommand(fallbackMethod = "fallbackuserroledelete")
	@Override
	public boolean delete(List<Integer> list)
	{
		String result = restTemplate.postForObject("http://" + serviceName + "/userrole/delete", list, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public boolean fallbackuserroledelete(List<Integer> list){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackuserroleselectAll")
	@Override
	public List<UserRole> selectAll()
	{
		List<UserRole> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/userrole/get", UserRole[].class));

		return list;
	}
	
	public List<Role> fallbackuserroleselectAll(){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return null;
	}

}
