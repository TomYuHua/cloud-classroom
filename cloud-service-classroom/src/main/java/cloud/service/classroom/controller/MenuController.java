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
import cloud.entity.classroom.every.Menu;
import cloud.service.classroom.interfaces.MenuService;
import cloud.service.classroom.interfaces.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController      
@RequestMapping(value = "/Menu")

public class MenuController
{
	private Logger log = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	

	@ApiOperation(value = "获取Menu", notes = "获取Menu")
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public List<Menu> selectAll()
	{
		try{
		     return menuService.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("RoleController", e);
			return null;
		}
	}
}