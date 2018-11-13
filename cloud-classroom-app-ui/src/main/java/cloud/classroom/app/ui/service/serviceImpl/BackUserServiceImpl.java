package cloud.classroom.app.ui.service.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.service.BackUserService;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.every.RequestParameter;
import cloud.entity.classroom.every.User;

@Service
public class BackUserServiceImpl implements BackUserService
{

	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;

	@HystrixCommand(fallbackMethod = "fallbackGetUserById")
	@Override
	public User getUserById(Integer id)
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getUserById?id=" + id, User.class);
	}

	public User fallbackGetUserById(Integer id)
	{
		User user = new User();
		System.out.println("HystrixCommand fallbackMethod handle!");
		return user;
	}

	@HystrixCommand(fallbackMethod = "fallbackAuditUser")
	@Override
	public boolean auditUser(String items, String type)
	{
		RequestParameter request = new RequestParameter();
		request.setItems(items);
		request.setType(type);
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/auditUser", request, String.class);
		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackAuditUser(String items, String type)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackMakeRole")
	@Override
	public boolean makeRole(Integer id, String type)
	{
		RequestParameter request = new RequestParameter();
		request.setId(id);
		request.setType(type);
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/makeRole", request, String.class);
		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackMakeRole(String items, String type)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;

	}

	@HystrixCommand(fallbackMethod = "fallbackSelectTypes")
	@Override
	public Integer selectTypes(String userName)
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/user/selectTypes?userName=" + userName, Integer.class);
	}

	public Integer fallbackSelectTypes(String userName)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetCollections")
	@Override
	public List<ResourcesVo> getCollections(Integer userId)
	{
		List<ResourcesVo> list = Arrays
				.asList(restTemplate.getForObject("http://" + SERVICE_NAME + "/resource/getCollections", ResourcesVo[].class, userId));
		return list;
	}

	public List<ResourcesVo> fallbackGetCollections(Integer userId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackAddUser")
	@Override
	public boolean addUser(User user)
	{
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/backuser/adduser", user, String.class);
		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	};

	public boolean fallbackAddUser(User user)
	{
		System.out.println("HystrixCommand fallbackAddUser handle!");

		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackBindUser")
	@Override
	public boolean bindUser(User user) {
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/backuser/binduser", user, String.class);
		if (result.equals("success")) {
			return true;
		} else {
			return false;
		}
	};
    
	
	public boolean fallbackBindUser(User user) {
		System.out.println("HystrixCommand fallbackAddUser handle!");

		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackChangeUserRole")
	@Override
	public boolean changeUserRole(String items,Integer userRoleId,Integer createUserId){
		                                                                          
		String result = restTemplate.getForObject("http://" + SERVICE_NAME + "/backuser/changeUserRole?items={items}&userRoleId={userRoleId}&createUserId={createUserId}", String.class,items,userRoleId,createUserId);
		if (result.equals("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean fallbackChangeUserRole(String items,Integer userRoleId,Integer createUserId) {
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}

}
