package cloud.classroom.app.ui.service.serviceImpl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.Controller.IndexController;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesBrower;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Collections;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.User;

@Service
public class ResourceServiceImpl implements ResourceService
{
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String serviceName;

	@Value("${dfs-fdfs-file-api}")
	private String fileAPI;
	
	@Value("${dfs-filesystem}")
	private String filesystem;

	private static Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

	@HystrixCommand(fallbackMethod = "fallbackselectAll")
	@Override
	public List<Resources> selectAll()
	{
		List<Resources> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/get", Resources[].class));

		List<Resources> lists = restTemplate.getForEntity("http://" + serviceName + "/resource/get", List.class).getBody();
		return list;
	}

	public List<Resources> fallbackselectAll()
	{
		System.out.println("HystrixCommand fallbackselectAll handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackselectYouLike")
	public List<Resources> selectYouLike(Integer userId, Integer m, Integer n)
	{
		List<Resources> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getyoulike?userId={userId}&m={m}&n={n}",
				Resources[].class, userId, m, n));
		return list;
	}

	public List<Resources> fallbackselectYouLike(Integer userId, Integer m, Integer n)
	{
		System.out.println("HystrixCommand fallbackselectYouLike handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackselectLimit")
	public List<Resources> selectLimit(Integer m, Integer n)
	{
		List<Resources> list = Arrays
				.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getlimit?m={m}&n={n}", Resources[].class, m, n));
		return list;
	}

	public List<Resources> fallbackselectLimit(Integer m, Integer n)
	{
		System.out.println("HystrixCommand fallbackselectLimit handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackshowResurces")
	@Override
	public Resources showResources(Integer id)
	{

		Resources resources = restTemplate.getForObject("http://" + serviceName + "/resource/showResurce?id={id}", Resources.class, id);
		return resources;
	}

	public Resources fallbackshowResurces(Integer id)
	{
		System.out.println("HystrixCommand fallbackshowResurces handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetResourcesList")
	@Override
	public List<Resources> getResourcesList(Resources resources)
	{

		return Arrays.asList(restTemplate.postForObject("http://" + serviceName + "/resource/getResourcesList", resources, Resources[].class));
	}

	public List<Resources> fallbackGetResourcesList(Resources resources)
	{
		System.out.println("HystrixCommand fallbackGetResourcesList handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetNLevelChildNode")
	public List<Resources> GetNLevelChildNode(Integer rootId, Integer n)
	{
		List<Resources> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getnlevelchildnode?rootId={rootId}&n={n}",
				Resources[].class, rootId, n));
		return list;
	}

	public List<Resources> fallbackGetNLevelChildNode(Integer rootId, Integer n)
	{

		System.out.println("HystrixCommand fallbackGetNLevelChildNode handle!");
		return null;
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallbackGetChildNodeList")
	public List<Resources> GetChildNodeList(Integer parentId)
	{
		List<Resources> list = Arrays.asList(
				restTemplate.getForObject("http://" + serviceName + "/resource/getchildnodelist?parentId={parentId}", Resources[].class, parentId));
		return list;
	}

	public List<Resources> fallbackGetChildNodeList(Integer parentId)
	{

		System.out.println("HystrixCommand fallbackGetChildNodeList handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetResourcesById")
	@Override
	public Resources getResourcesById(Integer id)
	{
		Resources resources = restTemplate.getForObject("http://" + serviceName + "/resource/getResourcesById?id={id}", Resources.class, id);
		return resources;
	}

	public Resources fallbackGetResourcesById(Integer id)
	{
		System.out.println("HystrixCommand fallbackGetChildNodeList handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackAddComments")
	@Override
	public boolean addComments(ResourceComentsVo resourcesComents)
	{

		String result = restTemplate.postForObject("http://" + serviceName + "/resource/addComments", resourcesComents, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackAddComments(ResourceComentsVo resourcesComents)
	{

		System.out.println("HystrixCommand fallbackAddComments handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackMakeResourceEva")
	@Override
	public boolean makeResourceEva(Resources resources)
	{

		String result = restTemplate.postForObject("http://" + serviceName + "/resource/makeResourceEva", resources, String.class);

		return false;
	}

	public boolean fallbackMakeResourceEva(Resources resources)
	{

		System.out.println("HystrixCommand fallbackMakeResourceEva handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackDeleteComments")
	@Override
	public boolean deleteComments(Integer id)
	{

		String result = restTemplate.postForObject("http://" + serviceName + "/resource/deleteComments", id, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}

	}

	public boolean fallbackDeleteComments(Integer id)
	{

		System.out.println("HystrixCommand fallbackDeleteComments handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetClusterResources")
	@Override
	public List<ResourcesVo> getClusterResources()
	{
		List<ResourcesVo> list = new ArrayList<ResourcesVo>();
		try
		{
			list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getClusterResources", ResourcesVo[].class));
			for (ResourcesVo resources : list)
			{
				Map<String, String> paramMap = Maps.newHashMap();
				paramMap.put("fileid", resources.getImgsrc());
				String url = fileAPI + "getSize";
				String result = HttpHelper.URLGet(url, paramMap, "utf-8");
				if (result != "")
				{
					MessageNotifyUtil messageNotifyUtil = new MessageNotifyUtil();

					messageNotifyUtil = JSON.parseObject(result, MessageNotifyUtil.class);
					String m = FileServiceConstant.FILE_SIZE;
					String a = messageNotifyUtil.getAppendMsg().get(m).toString();
					resources.setFileSize(a);
				} else
				{
					resources.setFileSize("225");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}

		return list;
	}

	public List<ResourcesVo> fallbackGetClusterResources()
	{

		System.out.println("HystrixCommand fallbackGetClusterResources handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetChildChpaterList")
	@Override
	public List<Resources> getChildChpaterList(Integer id)
	{
		List<Resources> list = Arrays
				.asList(restTemplate.postForObject("http://" + serviceName + "/resource/getChildChpaterList", id, Resources[].class));
		return list;
	}

	public List<Resources> fallbackGetChildChpaterList(Integer id)
	{
		System.out.println("HystrixCommand fallbackChildChpaterList handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetPageByTeacher")
	@Override
	public ResponseInfro getPageByTeacher(Integer userId)
	{
		ResponseInfro jSONObject = restTemplate.getForObject(
				"http://" + serviceName + "/resource/getPageByTeacher?userId={userId}", ResponseInfro.class,userId);
		return jSONObject;

	}

	public ResponseInfro fallbackGetPageByTeacher(Integer userId)
	{
		System.out.println("HystrixCommand fallbackGetPageByTeacher handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetCollections")
	@Override
	public List<ResourcesVo> getCollections(Integer userId, Integer types)
	{
		List<ResourcesVo> list = Arrays.asList(restTemplate.getForObject(
				"http://" + serviceName + "/resource/getCollections?userId={userId}&types={types}", ResourcesVo[].class, userId, types));
		return list;

	}

	public List<ResourcesVo> fallbackGetCollections(Integer userId, Integer types)
	{
		System.out.println("HystrixCommand fallbackGetCollections handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetParallelChpaterList")
	@Override
	public List<Resources> getParallelChpaterList(Integer currentid)
	{
		List<Resources> list = Arrays.asList(restTemplate
				.getForObject("http://" + serviceName + "/resource/getParallelChpaterList?currentid={currentid}", Resources[].class, currentid));
		return list;
	}

	public List<Resources> fallbackGetParallelChpaterList(Integer currentid)
	{
		System.out.println("HystrixCommand fallbackGetParallelChpaterList handle!");
		return null;
	}
	                                 
	@HystrixCommand(fallbackMethod = "fallbackAddCollections")
	@Override
	public boolean addCollections(Integer id,Integer userId,Integer types)
	{
		String result = restTemplate.getForObject("http://" + serviceName + "/resource/addCollections?id={id}&userId={userId}&types={types}",String.class,id,userId,types);
		if (result.equals("success"));
		return true;
	}

	public boolean fallbackAddCollections(Integer id,Integer userId,Integer types)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetCommentsByResourceId")
	@Override
	public List<ResourceComentsVo> getCommentsByResourceId(Integer id)
	{  	
	  List<ResourceComentsVo> comments = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getTreeNode?id={id}", ResourceComentsVo[].class, id));
		if (comments != null)
		{
	    for (ResourceComentsVo ca : comments)
	    {
		    String img = ca.getUserImg();
				if (img != null && img != "")
			   {
			     String imgPath = filesystem + img;
			     ca.setUserImg(imgPath);
			   }   
			Integer commtentsId=ca.getId();
			List<ResourceComentsVo> childComments =Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/queryTreeNode?id={commtentsId}",ResourceComentsVo[].class,commtentsId));
			for (ResourceComentsVo coment : childComments){
			String path = coment.getUserImg();
			if (path != null && path != ""){
			String imgUrl = filesystem + path;
			coment.setUserImg(imgUrl);
			}
		}
			ca.setNodes(childComments);
		}
	  }
		return comments;
		

	}

	public List<ResourceComentsVo> fallbackGetCommentsByResourceId(Integer id)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetLastLearn")
	@Override
	public List<DateResourcesVo> getLastLearn(Integer userId)
	{
		
		List<DateResourcesVo> list1 =Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getResourcesDate?userId={userId}", DateResourcesVo[].class, userId));

		List<ResourcesBrower> list2 =Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getBrowerByUserId?userId={userId}",ResourcesBrower[].class, userId));
		try
		{
			for (DateResourcesVo a1 : list1)
			{
				List<Resources> lists = new ArrayList<Resources>();
				for (ResourcesBrower a2 : list2)
				{
					if (a1.getWatchTime().equals(a2.getWatchTime()))
					{   Integer resourceId=a2.getResourceId();
					Resources resources =restTemplate.getForObject("http://" + serviceName + "/resource/getResourcesById?id={resourceId}", Resources.class,resourceId);
		

						String img = resources.getImgsrc();
						if (img != null && img != "")
						{
							String imgPath = filesystem + img;
							resources.setImgsrc(imgPath);
						}

						lists.add(resources);
					}
					a1.setBatchResources(lists);

				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list1;

	}

	public List<DateResourcesVo> fallbackGetLastLearn(Integer userId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetPersonalDownload")
	@Override
	public List<Resources> getPersonalDownload(Integer userId)
	{
		return Arrays.asList(
				restTemplate.getForObject("http://" + serviceName + "/resource/getPersonalDownload?userId={userId}", Resources[].class, userId));
	}

	public List<Resources> fallbackGetPersonalDownload(Integer userId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetPersonalCourse")
	@Override
	public List<Resources> getPersonalCourse(Integer id, Integer types)
	{
		return Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getPersonalCourse?id={id}&types={types}",
				Resources[].class, id, types));
	}

	public List<Resources> fallbackGetPersonalCourse(Integer id, Integer userId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	
	@HystrixCommand(fallbackMethod = "fallbackChangeClickCountState")
	@Override
	public boolean changeClickCountState (ResourcesBrower resourcesBrower){
		String result = restTemplate.postForObject("http://" + serviceName + "/resource/changeClickCountState",resourcesBrower,
				String.class);

		if (result.equals("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean fallbackChangeClickCountState (ResourcesBrower resourcesBrower) {
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}



	@HystrixCommand(fallbackMethod = "fallbackselectLimitByTimeOrder")
	@Override
	public List<Resources> selectLimitByTimeOrder(Integer types, Integer m, Integer n)
	{
		List<Resources> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/selectLimitByTimeOrder?types={types}&m={m}&n={n}",
				Resources[].class, types, m, n));
		return list;
	}

	public List<Resources> fallbackselectLimitByTimeOrder(Integer types, Integer m, Integer n)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}
    
	@HystrixCommand(fallbackMethod = "fallbackGetCollectionsByKey")
	@Override
	public Collections getCollectionsByKey(Integer resourceId,Integer userId){
		Collections colloctions=restTemplate.getForObject("http://"+serviceName+"/resource/isCollectedByLoginUser?resourceId={resourceId}&userId={userId}",Collections.class,resourceId,userId);
		return colloctions;
	}

	public Collections fallbackGetCollectionsByKey(Integer resourceId,Integer userId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}
	
    @HystrixCommand(fallbackMethod = "fallbackGetHeadlineList")
	@Override
	public List<Resources> getHeadlineList(Integer id){                                                     
		List<Resources> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/resource/getHeadlineList?id={id}",Resources[].class,id));
		return list;
	}
	
	public List<Resources> fallbackGetHeadlineList(Integer id)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}
	
    @HystrixCommand(fallbackMethod = "fallbackDeleteCollections")
	@Override
	public boolean deleteCollections(Integer id){                                                     
		String result = restTemplate.getForObject("http://" + serviceName + "/resource/deleteCollections?id={id}",String.class,id);
		if (result.equals("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean fallbackDeleteCollections(Integer id)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}
	
    @HystrixCommand(fallbackMethod = "fallbackIsExsitDocument")
	@Override
	public boolean isExsitDocument(Integer currentId){
		boolean result=restTemplate.getForObject("http://"+serviceName+"/resource/isExsitDocument?currentId={currentId}",boolean.class,currentId);
	      return result;
	}
	
	public boolean fallbackIsExsitDocument(Integer currentId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}
	
}
