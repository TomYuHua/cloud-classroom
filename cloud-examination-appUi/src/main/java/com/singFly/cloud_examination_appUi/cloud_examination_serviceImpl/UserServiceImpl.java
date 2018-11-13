package com.singFly.cloud_examination_appUi.cloud_examination_serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.UserService;
import com.singFly.cloud_examination_user.User;





@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	
	
	@HystrixCommand(fallbackMethod = "fallbackOperateUser")
	public boolean operateUser(UserVo user){
		return  restTemplate.postForObject("http://"+SERVICE_NAME +"/user/operateUser",user, boolean.class);
       
	}
	
	public boolean fallbackOperateUser(UserVo user)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackDeleteUsers")
	public boolean deleteUsers(String items){
		return  restTemplate.postForObject("http://"+SERVICE_NAME +"/user/deleteUsers",items, boolean.class);
       
	}
	
	public boolean fallbackDeleteUsers(String items)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetUser")
	public User getUser(int id,int type){
		return  restTemplate.getForObject("http://"+SERVICE_NAME +"/user/getUser?id={id}&type={type}",User.class,id,type);

	}
	
	public User fallbackGetUser(int id,int type)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackCheckExistMail")
	@Override
	public User checkExistMail(String email)
	{
		User user = restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getUserByUserName?userName={email}", User.class, email);
		return user;
	}

	public User fallbackCheckExistMail(String email)
	{
		User user = new User();

		System.out.println("HystrixCommand fallbackMethod handle!");

		return user;

	}
	@HystrixCommand(fallbackMethod = "fallbackResetPassWord")
	@Override
	public boolean resetPassWord(UserVo user) {
		return  restTemplate.postForObject("http://"+SERVICE_NAME +"/user/resetPassWord",user,boolean.class);
	}
	
	public boolean fallbackResetPassWord(UserVo user)
	{
	
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;

	}

}
