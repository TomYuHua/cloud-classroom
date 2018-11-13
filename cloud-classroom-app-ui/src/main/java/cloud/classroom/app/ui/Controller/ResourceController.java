package cloud.classroom.app.ui.Controller;



import static org.hamcrest.CoreMatchers.nullValue;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.Resources.Collections;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.Role;
import cloud.entity.classroom.every.User;
import cloud.classroom.app.ui.service.interfaces.ChapterService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/resource")
public class ResourceController
{
	private static Logger log = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	public ResourceService resourceService;

	@Autowired
	ChapterService chapterService;

	@Value("${dfs-filesystem}")
	private String filesystem;

	private static final String userInfo = "userInfoId";

	@RequestMapping("/resource")
	public String RoleInfo(Integer id)
	{

		return "/AdminBackend/resource";
	}
	
	public Resources showResources(Integer id)
	{

		return resourceService.showResources(id);
	}

	@RequestMapping(value = "/resourcelistpage")
	public String getResourcesList(Model model, Resources resources)
	{

		List<Resources> resourcesList = resourceService.getResourcesList(resources);
		for (Resources resource : resourcesList)
		{
			String imgPath = filesystem + resource.getImgsrc();
			resource.setImgsrc(imgPath);
		}

		model.addAttribute("resourcesList", resourcesList);

		return "/resource/resourcelist";

	}

	// @RequestMapping(value="/download")
	// public void download(HttpServletResponse response) throws Exception{
	// /**
	// * 这时候就需要通过url进行编码
	// */
	// ServletContext context = this.getServletContext();
	// //通过context方式直接获取文件的路径
	// String path = context.getRealPath("/download/美女.jpeg");
	// //获取文件名
	// String filename = path.substring(path.lastIndexOf("\\")+1);
	// //将文件名进行URL编码
	// filename = URLEncoder.encode(filename,"utf-8");
	// //告诉浏览器用下载的方式打开图片
	// response.setHeader("content-disposition",
	// "attachment;filename="+filename);
	// //将图片使用字节流的形式写给客户机
	// InputStream is =
	// this.getServletContext().getResourceAsStream("/download/美女.jpeg");
	// OutputStream out = response.getOutputStream();
	// byte[] buffer = new byte[1024];
	// int len = 0;
	// while((len=is.read(buffer))!=-1){
	// out.write(buffer, 0, len);
	// }
	//
	// }

	@RequestMapping(value = "/download")
	public void testDownload(HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String fileid = "group1/M00/00/00/wKgBylk6aAaEONwGAAAAAAAAAAA649.jpg";
		String url = "http://127.0.0.1:8080/download";
		OutputStream out = null;
		OutputStream os = null;
		try
		{
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileid.replaceAll("/", "_"));
			out = response.getOutputStream();
			Map<String, String> paramMap = Maps.newHashMap();
			paramMap.put("fileid", fileid);
			String result = HttpHelper.URLGet(url, paramMap, "utf-8");
			MessageNotifyUtil messageNotifyUtil = JSON.parseObject(result, MessageNotifyUtil.class);
			if (messageNotifyUtil.getCode() == 200)
			{
				String fileStr = (String) messageNotifyUtil.getAppendMsg().get("fileStr");
				byte[] buffer = Base64Helper.decode(fileStr);
				os = new BufferedOutputStream(out);
				os.write(buffer);
			}
		} finally
		{
			if (os != null)
			{
				try
				{

					os.close();

				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	List<Resources> list;

	@RequestMapping("gettreedata")
	@ResponseBody
	public String GetTreeData(Integer rootId)
	{
		try
		{
			list = resourceService.GetChildNodeList(rootId);
			List<Resources> result = list.stream().filter(x -> x.getIsdocument() == true).collect(Collectors.toList());
			StringBuilder sb = new StringBuilder();
			// sb.append("{ id:'', pId:1, name:"叶子节点1",
			// icon:"../../../css/zTreeStyle/img/diy/2.png"},")
			for (Resources c : result)
			{
				String str = "{id:'" + c.getId() + "',pId:'" + c.getParentId() + "',name:'" + c.getName() + "',isParent:true},";
				sb.append(str);
			}
			if (sb.length() > 0)
			{
				sb.insert(0, "[");
				sb.deleteCharAt(sb.length() - 1);
				// sb.substring(0, sb.length() - 2);
				sb.append("]");
				return sb.toString();
			} else
			{
				return "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
			return "";
		}

	}

	@RequestMapping("/getResourcescontent/{id}")
	public String getResourcesContent(Model model, @PathVariable("id") Integer id, HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		Resources resources = resourceService.getResourcesById(id);
		if (resources != null)
		{
			String img = resources.getImgsrc();
			if (img != null && img != "")
			{
				String imgPath = filesystem + resources.getResourcepath();
				resources.setImgsrc(imgPath);
			}

		}
		List<ResourcesVo> listresources = resourceService.getClusterResources();
		model.addAttribute("listresources", listresources);
		model.addAttribute("resources", resources);
		model.addAttribute("user", user);
		return "view3";
	}

	@RequestMapping("/getCourseDescribtions")
	public String getCoursesDescribtions(Model model,@RequestParam Integer id, HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);

		Resources resources = resourceService.getResourcesById(id);

		if(user!=null){
		        Collections collections=resourceService.getCollectionsByKey(id,user.getUserId());
			if(null!=collections){
			resources.setIsCollectedByLoginUser(true);
			model.addAttribute("collections",collections);
		 }
		}
		model.addAttribute("course", resources);

		return "/resource/courseDescribtions";
	}

	@RequestMapping("/getvideocontent/{id}")
	public String getvideocontent(Model model, @PathVariable("id") Integer id, HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);

		Resources resources = resourceService.getResourcesById(id);
		if (resources != null)
		{
			String img = resources.getImgsrc();
			if (img != null && img != "")
			{
				String imgPath = filesystem + resources.getResourcepath();
				resources.setImgsrc(imgPath);
			}

		}
		if(user!=null){
		if(resourceService.getCollectionsByKey(id,user.getUserId())!=null){
			resources.setIsCollectedByLoginUser(true);
		 }
		}
		model.addAttribute("resources", resources);
		model.addAttribute("user", user);
		return "/resource/video";
	}
	
	
	
	
	
	
	@RequestMapping("/deletecomments")
	@ResponseBody
	public Map<String, String> deletecomments(Integer id)
	{
		Map<String, String> map = new HashMap<String, String>();
		if (resourceService.deleteComments(id));
		map.put("result", "ok");
		return map;

	}
   
	@RequestMapping("/deleteCollections")
	@ResponseBody
	public Map<String, String> deleteCollections(Integer id)
	{
		Map<String, String> map = new HashMap<String, String>();
		if (resourceService.deleteCollections(id));
		map.put("result", "ok");
		return map;

	}
	
	@RequestMapping("GetNLevelTreeData")
	@ResponseBody
	public String GetNLevelTreeData(Integer rootId, Integer n)
	{
		try
		{
			List<Resources> list = resourceService.GetNLevelChildNode(rootId, n);
			// List<Resources> result = list.stream().filter(x ->
			// x.getIsdocument() == true).collect(Collectors.toList());
			StringBuilder sb = new StringBuilder();
			// sb.append("{ id:'', pId:1, name:"叶子节点1",
			// icon:"../../../css/zTreeStyle/img/diy/2.png"},")
			for (Resources c : list)
			{
				String str = "";
				if (c.getIsdocument() == true)
				{
					str = "{id:'" + c.getId() + "',pId:'" + c.getParentId() + "',name:'" + c.getName() + "',isParent:true},";
				} else
				{
					str = "{id:'" + c.getId() + "',pId:'" + c.getParentId() + "',name:'" + c.getName() + "'},";
				}

				sb.append(str);
			}
			if (sb.length() > 0)
			{
				sb.insert(0, "[");
				sb.deleteCharAt(sb.length() - 1);
				// sb.substring(0, sb.length() - 2);
				sb.append("]");
				return sb.toString();
			} else
			{
				return "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
			return "";
		}

	}

	@RequestMapping(value = "/menu")
	public String getResourcesList(Model model)
	{ 	
  
       model.addAttribute("myebook", true);
		 return "/PersonalCenter/Menu";

	}

	@RequestMapping("/getResourcesById")
	@ResponseBody
	public Resources getResourcesById(Integer id)
	{
		Resources resources = new Resources();
		try
		{
			resources = resourceService.getResourcesById(id);
			if (resources != null)
			{
				String resourcepath = resources.getResourcepath();
				if (resourcepath != null && resourcepath != "")
				{
					String videoPath = filesystem + resources.getResourcepath();
					resources.setResourcepath(videoPath);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
	
		}

		return resources;

	}

	@RequestMapping("/getvideo")
	@ResponseBody
	public String getVideoResourcesById(Integer id)
	{
		String videoPath = "";
		try
		{
			Resources resources = resourceService.getResourcesById(id);
			if (resources != null)
			{
				String resourcepath = resources.getResourcepath();
				if (resourcepath != null && resourcepath != "")
				{
					videoPath = filesystem + resources.getResourcepath();
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
		}

		return videoPath;

	}

	@RequestMapping("/addcomments")
	@ResponseBody
	public Map<String, String> addcomments(@RequestBody ResourceComentsVo resourceComents, HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		HttpSession sessions = request.getSession();
		try{
		User user = (User) sessions.getAttribute(userInfo);
	     resourceComents.setUserId(user.getUserId());

		if (resourceService.addComments(resourceComents)){
			map.put("result", "ok");
		}else{
			map.put("result", "fail");
		  }
		
		}catch (Exception e){
			e.printStackTrace();
		};
	    	return map;

	}

	@RequestMapping(value = "/makeResourceEva", method = RequestMethod.POST)
	@ResponseBody
	public String makeResourceEva(Resources resources)
	{

		if (resourceService.makeResourceEva(resources))
			;

		return "success";

	}

	@RequestMapping(value = "/getCollections")
	public String getCollections(@RequestParam int types, Model model, HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		try
		{
			List<ResourcesVo> list = resourceService.getCollections(user.getUserId(), types);
			for (ResourcesVo resource : list)
			{
				if (null != resource.getImgsrc())
				{
					String imgPath = filesystem + resource.getImgsrc();
					resource.setImgsrc(imgPath);
				}
			}

			model.addAttribute("list", list);

		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("getCollections", e);
		}
		if (types == 1)
		{
			return "/PersonalCenter/collectionVideo";
		} else
		{
			return "/PersonalCenter/collectionEbook";
		}
	}

	@RequestMapping("/addCollections")
	@ResponseBody
	public Map<String, String> addCollections(Integer id,Integer types,HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		if (resourceService.addCollections(id, user.getUserId(),types))
			;
		map.put("result", "ok");
		return map;

	}

	@RequestMapping("/checkresourcepemission")
	@ResponseBody
	public Map<String, String> checkresourcepemission(String checkNo,HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		if(user!=null){
		List<String> lists=user.getMenuNoLists();
		if (lists.indexOf(checkNo)!=-1){
			map.put("result", "ok");
		}else{
			map.put("result", "no");
		}
		}else{
			map.put("result", "no");
		}
		   return map;

	}

}
