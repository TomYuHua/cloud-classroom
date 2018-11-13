package cloud.service.classroom.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.ResourceAdminService;
import cloud.service.classroom.interfaces.ResourceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController       
@RequestMapping(value = "/Resource")
public class ResourceAdminController
{
	private Logger log = LoggerFactory.getLogger(ResourceAdminController.class);

	@Autowired
	private ResourceAdminService resourceAdminService;

	@ApiOperation(value = "插入资源", notes = "插入资源")
	@ApiImplicitParam(name = "record", value = "资源实体类", required = true, dataType = "Resources")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public int insert(@RequestBody Resources record)
	{
		try
		{
			return resourceAdminService.insert(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceAdminController", e);
			return -1;
		}
	}
	
	@ApiOperation(value = "插入资源file", notes = "插入资源file")
	@ApiImplicitParam(name = "record", value = "file", required = true, dataType = "Resources")
	@RequestMapping(value = "/insertfile", method = RequestMethod.POST)
	public int insertfile(@RequestBody Resources record)
	{
		try
		{
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			record.setCreatetime(d);
			return resourceAdminService.insertfile(record);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceAdminController", e);
			return -1;
		}
	}
	
	@ApiOperation(value = "资源上传files", notes = "资源上传files")
	@ApiImplicitParam(name = "resources", value = "files", required = true, dataType = "Resources")
	@RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
	public int uploadfiles(@RequestBody List<Resources> resources)
	{
		try
		{
			return resourceAdminService.uploadfiles(resources);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceAdminController", e);
			return -1;
		}
	}
	
	@ApiOperation(value = "资源上传text", notes = "资源上传text")
	@ApiImplicitParam(name = "resources", value = "text", required = true, dataType = "Resources")
	@RequestMapping(value = "/uploadtext", method = RequestMethod.POST)
	public int uploadtext(@RequestBody Resources resources)
	{
		try
		{
			return resourceAdminService.uploadtext(resources);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceAdminController", e);
			return -1;
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestBody List<Integer> list)
	{
		if(resourceAdminService.delete(list)){
			 return "success";
		     }else{
		     return "fail"; 
		 }
	}
	
	@RequestMapping(value = "/getContent", method = RequestMethod.GET)
	@ResponseBody
	public String getContent(Integer id)
	{
		
		return resourceAdminService.getContent(id);
	}
	
	//@ApiOperation(value = "修改资源", notes = "修改资源的describes")
	//@ApiImplicitParam(name = "resources",value="change_describes",required=false,dataType="Resources")
	@RequestMapping(value = "/changeResources",method=RequestMethod.POST)
	public String changeResources(@RequestBody Resources resources)
	{
        if(resourceAdminService.changeResources(resources))
        {
        	return "success";
        }else{
        	return "false";
        }
	}
	
	@ApiOperation(value = "修改资源", notes = "修改资源的file")
	@ApiImplicitParam(name = "resources", value = "changefile", required = true, dataType = "Resources")
	@RequestMapping(value = "/changefile", method = RequestMethod.POST)
	public String changefile(@RequestBody Resources resources)
	{
        if(resourceAdminService.changefile(resources))
        {
        	return "success";
        }else{
        	return "false";
        }
	}
	
	@ApiOperation(value = "审查资源", notes = "审查资源的status")
	@ApiImplicitParam(name = "resources", value = "check_status", required = true, dataType = "Resources")
	@RequestMapping(value = "/checkResources", method = RequestMethod.POST)
	public String checkResources(@RequestBody Resources resources)
	{
		if(resourceAdminService.checkResources(resources))
		{
			return "success";
		}else{
			return "false";
		}
	}
	
	@ApiOperation(value = "获取资源", notes = "获取所有资源")
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public List<Resources> selectAll()
	{
		try
		{
			return resourceAdminService.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceController", e);
			return null;
		}
	}

}