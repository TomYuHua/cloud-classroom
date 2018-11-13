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

import cloud.classroom.app.ui.service.interfaces.MenuService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.classroom.app.ui.service.interfaces.RoleService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.Menu;

@Service
public class MenuServiceImpl implements MenuService
{
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cloud-user-service}")
	private String serviceName;

	@HystrixCommand(fallbackMethod = "fallbackroleselectAll")
	@Override
	public List<Menu> selectAll() {
		List<Menu> list = Arrays
				.asList(restTemplate.getForObject("http://" + serviceName + "/Menu/selectAll", Menu[].class));

		return list;
	}
	
	public List<Menu> fallbackroleselectAll(){
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		
		return null;
	}

}