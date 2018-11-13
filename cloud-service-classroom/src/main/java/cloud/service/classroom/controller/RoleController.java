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
import cloud.service.classroom.interfaces.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController      
@RequestMapping(value = "/Role")

public class RoleController
{
	private Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@ApiOperation(value = "插入角色", notes = "插入角色")
	@ApiImplicitParam(name = "record", value = "角色插入", required = true, dataType = "Role")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public int insert(@RequestBody Role record)
	{
		try
		{
			return roleService.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("Role explore", e);
			return -1;
		}
	}
	
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody List<Integer> list)
	{
		if(roleService.delete(list)){
			 return "success";
		     }else{
		     return "false";
		 }
	}
	
	@ApiOperation(value = "修改角色", notes = "修改角色")
	@ApiImplicitParam(name = "role", value = "change_describes", required = false, dataType = "Role")
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public String change(@RequestBody Role role)
	{
        if(roleService.change(role))
        {
		return "success";
        }else{
        	return "false";
        }
	}
	
	@ApiOperation(value = "获取角色", notes = "获取所有角色")
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public List<Role> selectAll()
	{
		try{
		     return roleService.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RoleController", e);
			return null;
		}
	}
}