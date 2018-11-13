package cloud.classroom.app.ui.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.service.BackTeacherService;
import cloud.entity.classroom.every.User;

@Service
public class BackTeacherServiceImpl implements BackTeacherService {
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	
	
	@HystrixCommand(fallbackMethod = "fallbackInsertTeacher")
	@Override
	public boolean insertTeacher(User user)
	{
	   String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/backuser/adduser", user, String.class);
		 if (result.equals("success"))
		{
		  return true;
		} else{
		   return false;
		}
	}

	public boolean fallbackInsertTeacher(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackInsertTeacher")
	@Override
	public boolean updateTeacher(User user)
	{
	
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/teacher/updateteacher", user, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackUpdateTeacher(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackDeleteTeacher")
	@Override
	public boolean deleteTeacher(Integer id)
	{
	
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/teacher/updateteacher", id, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackDeleteTeacher(Integer id)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}

}
