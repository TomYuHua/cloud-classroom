package cloud.classroom.app.ui.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.classroom.app.ui.service.interfaces.RoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.Role;

@Service
public class RoleServiceImpl implements RoleService
{
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cloud-user-service}")
	private String serviceName;

	@HystrixCommand(fallbackMethod = "fallbackroleinsert")
	@Override
	public int insert(Role record) {
		int result = restTemplate.postForObject("http://" + serviceName + "/Role/insert", record, int.class);

		return result;
	}
	
	public int fallbackroleinsert(Role record){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return 0;
	}

	@HystrixCommand(fallbackMethod = "fallbackroleselectAll")
	@Override
	public List<Role> selectAll() {
		List<Role> list = Arrays
				.asList(restTemplate.getForObject("http://" + serviceName + "/Role/selectAll", Role[].class));

		return list;
	}
	
	public List<Role> fallbackroleselectAll(){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackroledelete")
	@Override
	public boolean delete(List<Integer> list) {
		
		String result = restTemplate.postForObject("http://" + serviceName + "/Role/delete", list,
				String.class);

		if (result.equals("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean fallbackroledelete(List<Integer> list){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return false;
	}

//	@HystrixCommand(fallbackMethod = "fallbackchangerole")
	@Override
	public boolean change(Role role) {
		
		try{
			String result = restTemplate.postForObject("http://"+serviceName+"/Role/change",role,String.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean fallbackchangerole(Role role){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return false;
	}
	
}