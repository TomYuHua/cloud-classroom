package cloud.classroom.app.ui.service.serviceImpl;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.Controller.IndexController;
import cloud.classroom.app.ui.service.interfaces.ResourceAdminService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.common.constant.FileServiceConstant;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourceComentsVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.ResourceComents;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.User;

@Service
public class ResourceAdminServiceImpl implements ResourceAdminService
{
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String serviceName;

	private static Logger log = LoggerFactory.getLogger(ResourceAdminServiceImpl.class);

	@HystrixCommand(fallbackMethod = "fallbackinsert")
	@Override
	public int insert(Resources record)
	{
		int result = restTemplate.postForObject("http://" + serviceName + "/Resource/insert", record, int.class);

		return result;
	}
	
	public int fallbackinsert(Resources record)
	{
		System.out.println("HystrixCommand fallbackinsert handle!");
		return -1;
	}

	@HystrixCommand(fallbackMethod = "fallbackinsertfile")
	@Override
	public int insertfile(Resources record) {
		int result = restTemplate.postForObject("http://" + serviceName + "/Resource/insertfile", record, int.class);
		
		return result;
	}
	
	public int fallbackinsertfile(Resources record)
	{
		System.out.println("HystrixCommand fallbackinsert handle!");
		return -1;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackdelete")
	@Override
	public boolean delete(List<Integer> list)
	{
		String result = restTemplate.postForObject("http://" + serviceName + "/Resource/delete", list, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public boolean fallbackdelete(List<Integer> list)
	{
		System.out.println("HystrixCommand fallbackinsert handle!");
		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackgetcontent")
	@Override
	public String getContent(Integer id)
	{
		
		return restTemplate.getForObject("http://" + serviceName + "/Resource/getContent?id=" + id, String.class);
	}
	
	public String fallbackgetcontent(Integer id)
	{
		System.out.println("HystrixCommand fallbackgetcontent handle!");
		String bug = "wonder : this is wrong!!!";
		return bug;
	}

	//@HystrixCommand(fallbackMethod = "fallbackchangeresource")
	@Override
	public boolean changeResources(Resources resources)
	{

		String result=restTemplate.postForObject("http://"+serviceName+"/Resource/changeResources",resources,String.class);
		

		if (result.equals("success"))
		{  
			return true;
		}else
		{
			return false;
		}
	}
	
	public boolean fallbackchangeresource(Resources resources)
	{
		System.out.println("HystrixCommand fallbackChange handle!");
		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackchangefile")
	@Override
	public boolean changefile(Resources resources)
	{

		String result = restTemplate.postForObject("http://"+serviceName+"/Resource/changefile",resources, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public boolean fallbackchangefile(Resources resources)
	{
		System.out.println("HystrixCommand fallbackinsert handle!");
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackcheck")
	@Override
	public boolean checkResources(Resources resources)
	{

		String result = restTemplate.postForObject("http://" + serviceName + "/Resource/checkResources", resources, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public boolean fallbackcheck(Resources resources)
	{
		System.out.println("HystrixCommand fallbackinsert handle!");
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackselectAll")
	@Override
	public List<Resources> selectAll()
	{
		List<Resources> list = Arrays.asList(restTemplate.getForObject("http://" + serviceName + "/Resource/selectAll", Resources[].class));

		return list;
	}

	public List<Resources> fallbackselectAll()
	{
		System.out.println("HystrixCommand fallbackselectAll handle!");
		return null;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackuploadfiles")
	@Override
	public boolean uploadfiles(List<Resources> resources)
	{
		String result = restTemplate.postForObject("http://" + serviceName + "/Resource/uploadfiles", resources, String.class);
		
		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackuploadfiles(List<Resources> resources)
	{
		System.out.println("HystrixCommand fallbackuploadfiles handle!");
		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackuploadtext")
	@Override
	public boolean uploadtext(Resources resources)
	{
		String result = restTemplate.postForObject("http://" + serviceName + "/Resource/uploadtext", resources, String.class);
		
		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackuploadtext(Resources resources)
	{
		System.out.println("HystrixCommand fallbackuploadtext handle!");
		return false;
	}
}
