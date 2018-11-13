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
import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Collections;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.PageInfro;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.ResourceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/resource")
public class ResourceController
{
	private Logger log = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	@ApiOperation(value = "分页显示资源", notes = "分页显示资源")
	@ApiImplicitParam(name = "getPageSources", value = "getPageSources", required = true, dataType = "Resources")
	@RequestMapping(value = "/getPageSources", method = RequestMethod.GET)
	public List<Resources> showResources(@RequestBody Resources resources)
	{
		try
		{
			return resourceService.showResources(resources);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("resource over stack", e);
			return null;
		}
	}

	@ApiOperation(value = "获取资源", notes = "获取所有资源")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public List<Resources> selectAll()
	{
		try
		{
			log.error("get进来方法前");
			return resourceService.selectAll();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceController", e);
			return null;
		}
	}

	@ApiOperation(value = "获取资源", notes = "获取所有资源 m n")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "m", dataType = "int", required = true, value = "获取记录位置", defaultValue = "0"),
	@ApiImplicitParam(paramType = "query", name = "n", dataType = "int", required = true, value = "获取总条数", defaultValue = "2") })
	@RequestMapping(value = "/getlimit", method = RequestMethod.GET)
	public List<Resources> selectLimit(int m, int n)
	{
		try
		{
			log.error("getlimit进来方法前");
			return resourceService.selectLimit(m, n);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceController", e);
			return null;
		}
	}

	@ApiOperation(value = "获取资源", notes = "获取你喜欢的资源 m n")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID"),
	@ApiImplicitParam(paramType = "query", name = "m", dataType = "int", required = true, value = "获取记录位置"),
	@ApiImplicitParam(paramType = "query", name = "n", dataType = "int", required = true, value = "获取总条数") })
	@RequestMapping(value = "/getyoulike", method = RequestMethod.GET)
	public List<Resources> selectYouLike(Integer userId, int m, int n)
	{
		try
		{
			return resourceService.selectYouLike(userId, m, n);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceController", e);
			return null;
		}
	}

	@ApiOperation(value = "用户资源", notes = "通过章节显示所有资源")
	@ApiImplicitParam(name = "resources", value = "请求参数", required = false, dataType = "Resources")
	@RequestMapping(value = "/getResourcesList", method = RequestMethod.POST)
	public List<Resources> getResourcesList(@RequestBody Resources resources)
	{

		try
		{
			List<Resources> list = resourceService.getResourcesList(resources);
			String a = "c";
			return list;

		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("ResourceController", e);
			return null;
		}

	}

	@ApiOperation(value = "目录", notes = "获取目录")
	@ApiImplicitParam(paramType = "query", name = "parentId", value = "0", required = true, dataType = "Integer", defaultValue = "0")
	@RequestMapping(value = "/getchildnodelist", method = RequestMethod.GET)
	public List<Resources> GetChildNodeList(Integer parentId)
	{
		try
		{
			return resourceService.GetChildNodeList(parentId);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
			return new ArrayList<Resources>();
		}

	}

	@ApiOperation(value = "获取N级节点数据", notes = "获取N级节点数据")
    @ApiImplicitParams({
    @ApiImplicitParam(paramType = "query", name = "rootId", dataType = "Integer", required = true, value = "根节点"),
    @ApiImplicitParam(paramType = "query", name = "n", dataType = "Integer", required = true, value = "获取N级") })
	@RequestMapping(value = "/getnlevelchildnode", method = RequestMethod.GET)
	public List<Resources> GetNLevelChildNode(Integer rootId, Integer n)
	{
		try
		{

			List<Resources> list = resourceService.GetNLevelChildNode(rootId, n);
		
			return list;
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
			return new ArrayList<Resources>();
		}

	}

	@ApiOperation(value = "文档显示页", notes = "文档显示页")
	@ApiImplicitParam(name = "id", value = "文档id", required = false, dataType = "Integer")
	@RequestMapping(value = "/getResourcesById", method = RequestMethod.GET)
	public Resources getResourcesById(Integer id)
	{

		return resourceService.getResourcesById(id);

	}

	@ApiOperation(value = "添加评论", notes = "添加评论")
	@ApiImplicitParam(name = "resourceComents", value = "评论实体类", required = false, dataType = "ResourceComents")
	@RequestMapping(value = "/addComments", method = RequestMethod.POST)
	public String addComments(@RequestBody ResourceComentsVo resourceComents)
	{
		String a = "c";
		if (resourceService.addComments(resourceComents))
			;

		return "success";

	}

	@ApiOperation(value = "文档评分", notes = "文档评分")
	@ApiImplicitParam(name = "课程资源", value = "课程资源", required = false, dataType = "Resources")
	@RequestMapping(value = "/makeResourceEva", method = RequestMethod.POST)
	public String makeResourceEva(@RequestBody Resources resources)
	{

		if (resourceService.makeResourceEva(resources))
			;

		return "success";

	}

	@ApiOperation(value = "删除评论", notes = "删除评论")
	@ApiImplicitParam(name = "id", value = "评论的id号", required = false, dataType = "Integer")
	@RequestMapping(value = "/deleteComments", method = RequestMethod.POST)
	public String deleteComments(@RequestBody Integer id)
	{

		if (resourceService.deleteComments(id))
		{
			return "success";
		} else
		{
			return "fail";
		}

	}

	@RequestMapping(value = "/getClusterResources")
	public List<ResourcesVo> getClusterResources()
	{
		return resourceService.getClusterResources();
	}

	@ApiOperation(value = "获取下一级所有章节", notes = "获取下一级所有章节")
	@ApiImplicitParam(name = "资源id", value = "资源id", required = false, dataType = "Integer")
	@RequestMapping(value = "/getChildChpaterList", method = RequestMethod.POST)
	public List<Resources> getChildChpaterList(@RequestBody Integer id)
	{
		return resourceService.getChildChpaterList(id);
	}

	@ApiOperation(value = "获取平级章节", notes = "获取平级章节")
	@ApiImplicitParam(paramType = "query", name = "currentid", value = "资源id", required = false, dataType = "Integer")
	@RequestMapping(value = "/getParallelChpaterList", method = RequestMethod.GET)
	public List<Resources> getParallelChpaterList(Integer currentid)
	{
		return resourceService.getParallelChpaterList(currentid);
	}

	@ApiOperation(value = "获取资源", notes = "获取某类型前几条资源 m n")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "types", dataType = "Integer", required = true, value = "类型", defaultValue = "1"),
			@ApiImplicitParam(paramType = "query", name = "m", dataType = "int", required = true, value = "获取记录位置", defaultValue = "0"),
			@ApiImplicitParam(paramType = "query", name = "n", dataType = "int", required = true, value = "获取总条数", defaultValue = "2") })
	@RequestMapping(value = "/selectLimitByTimeOrder", method = RequestMethod.GET)
	public List<Resources> selectLimitByTimeOrder(Integer types, Integer m, Integer n)
	{
		return resourceService.selectLimitByTimeOrder(types, m, n);
	}

	@ApiOperation(value = "获取用户个人的收藏", notes = "获取用户个人的收藏")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户的Id"),
			@ApiImplicitParam(paramType = "query", name = "types", dataType = "Integer", required = true, value = "资源的类型") })
	@RequestMapping(value = "/getCollections", method = RequestMethod.GET)
	public List<ResourcesVo> getCollections(Integer userId, Integer types) throws Exception
	{
		String a = "c";
		return resourceService.getCollection(userId, types);
	}

	@ApiOperation(value = "获取单个老师所有上传的资源", notes = "获取单个老师所有上传的资源")
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户Id", defaultValue = "5")
	@RequestMapping(value = "/getPageByTeacher", method = RequestMethod.GET)
	public ResponseInfro getPageByTeacher(Integer userId) throws Exception
	{
		Page<ResourcesVo> resourceList = resourceService.getPageByTeacher(userId);

		ResponseInfro information = new ResponseInfro();
		PageInfro pageInfro = new PageInfro();
		pageInfro.setPageNum(String.valueOf(resourceList.getPageNum()));
		pageInfro.setPages(String.valueOf(resourceList.getPages()));
		pageInfro.setPageSize(String.valueOf(resourceList.getPageSize()));
		pageInfro.setTotal(String.valueOf(resourceList.getTotal()));
		information.dataPage = resourceList.getResult();
		information.pageInfro = pageInfro;
		information.error = "0";

		// JSONObject jsonObj = new JSONObject();

		// jsonObj.put("information",information);

		return information;
	}

	@ApiOperation(value = "添加收藏", notes = "添加收藏")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "资源id"),
			@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id") })
	@RequestMapping(value = "/addCollections", method = RequestMethod.GET)
	public String addCollections(Integer id, Integer userId, Integer types)
	{
		if (resourceService.addCollections(id, userId, types))
		{
			return "success";
		} else
		{
			return "fail";
		}
	}

	@ApiOperation(value = "判断登录用户是否收藏某资源", notes = "判断登录用户是否收藏某资源")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "resourceId", dataType = "Integer", required = true, value = "资源id"),
			@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id") })
	@RequestMapping(value = "/isCollectedByLoginUser", method = RequestMethod.GET)
	public Collections isCollectedByLoginUser(Integer resourceId, Integer userId)
	{
		return resourceService.isCollectedByLoginUser(resourceId, userId);

	}

	@ApiOperation(value = "添加浏览记录", notes = "添加浏览记录")
	@ApiImplicitParam(name = "浏览记录", value = "浏览记录", required = false, dataType = "ResourcesBrower")
	@RequestMapping(value = "/changeClickCountState", method = RequestMethod.POST)
	public String changeClickCountState(@RequestBody ResourcesBrower resourcesBrower)
	{
		if (resourceService.changeClickCountState(resourcesBrower))
		{
			return "success";
		} else
		{
			return "fail";
		}
	}

	@ApiOperation(value = "通过资源id取得一级评论", notes = "通过资源id取得一级评论")
	@ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "资源id")
	@RequestMapping(value = "/getTreeNode", method = RequestMethod.GET)
	public List<ResourceComentsVo> getTreeNode(Integer id)
	{
		return resourceService.getTreeNode(id);
	}

	
	@ApiOperation(value = "通过资源id取得二级评论", notes = "通过资源id取得二级评论")
	@ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "资源id")
	@RequestMapping(value = "/queryTreeNode", method = RequestMethod.GET)
	public List<ResourceComentsVo> queryTreeNode(Integer id)
	{
		return resourceService.queryTreeNode(id);
	}

	@ApiOperation(value = "某个用户浏览的日期", notes = "某个用户浏览的日期")
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id")
	@RequestMapping(value = "/getResourcesDate", method = RequestMethod.GET)
	public List<DateResourcesVo> getResourcesDate(Integer userId)
	{
		return resourceService.getResourcesDate(userId);
	}
	
	@ApiOperation(value = "某个用户浏览的课程资源", notes = "某个用户浏览的课程资源 ")
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id")
	@RequestMapping(value = "/getBrowerByUserId", method = RequestMethod.GET)
	public List<ResourcesBrower> getBrowerByUserId(Integer userId)
	{
		return resourceService.getBrowerByUserId(userId);
	}
	

	@ApiOperation(value = "个人的下载", notes = "个人的下载")
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id")
	@RequestMapping(value = "/getPersonalDownload", method = RequestMethod.GET)
	public List<Resources> getPersonalDownload(Integer userId)
	{

		return resourceService.getPersonalDownload(userId);
	}

	@ApiOperation(value = "个人的课程", notes = "个人的课程")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "用户id"),
			@ApiImplicitParam(paramType = "query", name = "types", dataType = "Integer", required = true, value = "资源类型") })
	@RequestMapping(value = "/getPersonalCourse", method = RequestMethod.GET)
	public List<Resources> getPersonalCourse(Integer id, Integer types)
	{
		return resourceService.getPersonalCourse(id, types);
	}

	@ApiOperation(value = "资源的导航", notes = "资源的导航")
	@ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "资源id")
	@RequestMapping(value = "/getHeadlineList", method = RequestMethod.GET)
	public List<Resources> getHeadlineList(Integer id)
	{
		return resourceService.getHeadlineList(id);
	}

	@ApiOperation(value = "删除收藏", notes = "删除收藏")
	@ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "收藏id")
	@RequestMapping(value = "/deleteCollections", method = RequestMethod.GET)
	public String deleteCollections(Integer id)
	{
		if (resourceService.deleteCollections(id))
		{
			return "success";
		} else
		{
			return "fail";
		}
	}

	@ApiOperation(value = "下一级是否存在文件夹", notes = "下一级是否存在文件夹")
	@ApiImplicitParam(paramType = "query", name = "currentId", dataType = "Integer", required = true, value = "传入的Id")
	@RequestMapping(value = "/isExsitDocument", method = RequestMethod.GET)
	public boolean isExsitDocument(Integer currentId)
	{
		return resourceService.isExsitDocument(currentId);
	}
}