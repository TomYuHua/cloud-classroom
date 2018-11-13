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
import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.RolePrivilege;
import cloud.service.classroom.interfaces.RolePrivilegeService;
import cloud.service.classroom.interfaces.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController      
@RequestMapping(value = "/RolePrivilege")

public class RolePrivilegeController
{
	private Logger log = LoggerFactory.getLogger(RolePrivilegeController.class);
	
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	
	@ApiOperation(value = "插入角色-权限", notes = "插入角色-权限")
	@ApiImplicitParam(name = "Role_Privilege", value = "角色插入-权限", required = true, dataType = "Role-Privilege")
	@RequestMapping(value = "/insertRolePrivilege", method = RequestMethod.POST)
	public int insert(@RequestBody RolePrivilege record)
	{
		try
		{
			return rolePrivilegeService.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RolePrivilege explore", e);
			return -1;
		}
	}
	
	@RequestMapping(value = "/deleteRolePrivilege", method = RequestMethod.POST)
	public String delete(@RequestBody List<Integer> list)
	{
		if(rolePrivilegeService.delete(list)){
			 return "success";
		     }else{
		     return "fail"; 
		 }
	}
	
	@ApiOperation(value = "获取角色-权限", notes = "获取所有角色-权限")
	@RequestMapping(value = "/selectAllRolePrivilege", method = RequestMethod.GET)
	public List<RolePrivilege> selectAll()
	{
		try
		{
		    return rolePrivilegeService.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RolePrivilegeController", e);
			return null;
		}
	}
	
	@ApiOperation(value= "获取选定角色-权限", notes = "获取选定角色-权限")
	@RequestMapping(value = "/selectSelectRolePrivilege", method = RequestMethod.GET)
	public List<RolePrivilege> selectPiece(Integer roleid)
	{
		try
		{
			return rolePrivilegeService.selectPiece(roleid);
		}catch (Exception e)
		{
			e.printStackTrace();
			log.error("RolePrivilegeControllerpiece", e);
			return null;
		}
	}
}
