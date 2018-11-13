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
import cloud.classroom.app.ui.service.interfaces.RolePrivilegeService;
import cloud.classroom.app.ui.service.interfaces.RoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.RolePrivilege;

@Service
public class RolePrivilegeServiceImpl implements RolePrivilegeService
{
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cloud-user-service}")
	private String serviceName;

	@HystrixCommand(fallbackMethod = "fallbackroleprivilegeinsert")
	@Override
	public int insert(RolePrivilege record) {
		int result = restTemplate.postForObject("http://" + serviceName + "/RolePrivilege/insert", record, int.class);

		return result;
	}
	
	public int fallbackroleprivilegeinsert(RolePrivilege record){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return 0;
	}

	@HystrixCommand(fallbackMethod = "fallbackroleprivilegeselectAll")
	@Override
	public List<RolePrivilege> selectAll() {
		List<RolePrivilege> list = Arrays
				.asList(restTemplate.getForObject("http://" + serviceName + "/RolePrivilege/get", RolePrivilege[].class));

		return list;
	}
	
	public List<RolePrivilege> fallbackroleprivilegeselectAll(){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackroleprivilegedelete")
	@Override
	public boolean delete(List<Integer> list) {
		String result = restTemplate.postForObject("http://" + serviceName + "/RolePrivilege/delete", list,
				String.class);

		if (result.equals("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean fallbackroleprivilegedelete(List<Integer> list){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackroleprivilegeselectpiece")
	@Override
	public List<RolePrivilege> selectPiece(Integer roleid) {
		List<RolePrivilege> list = Arrays.asList(restTemplate
				.getForObject("http://" + serviceName + "/RolePrivilege/selectPiece?roleid={roleid}", RolePrivilege[].class, roleid));

		return list;
	}
	
	public boolean fallbackroleprivilegeselectpiece(Integer roleid){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return false;
	}
	
}
