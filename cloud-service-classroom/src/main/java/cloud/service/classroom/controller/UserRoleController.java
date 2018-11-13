package cloud.service.classroom.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.PageInfro;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.RolePrivilege;
import cloud.entity.classroom.every.UserRole;
import cloud.service.classroom.interfaces.RolePrivilegeService;
import cloud.service.classroom.interfaces.UserRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController      
@RequestMapping(value = "/UserRole")

public class UserRoleController
{
	private Logger log = LoggerFactory.getLogger(UserRoleController.class);
	
	@Autowired
	private UserRoleService userRoleService;
	
	@ApiOperation(value = "插入用户-角色", notes = "插入用户-角色")
	@ApiImplicitParam(name = "record", value = "插入用户-角色", required = true, dataType = "User_Role")
	@RequestMapping(value = "/insertUserRole", method = RequestMethod.POST)
	public int insert(@RequestBody UserRole record)
	{
		try
		{
			return userRoleService.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RolePrivilege explore", e);
			return -1;
		}
	}
	
	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
	public String delete(@RequestBody List<Integer> list)
	{
		if(userRoleService.delete(list)){
			 return "success";
		     }else{
		     return "fail"; 
		 }
	}
	
	@ApiOperation(value = "获取用户-角色", notes = "获取所有用户-角色")
	@RequestMapping(value = "/selectAllRolePrivilege", method = RequestMethod.GET)
	public List<UserRole> selectAll()
	{
		try{
		     return userRoleService.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("UserRoleController", e);
			return null;
		}
	}
	
}


