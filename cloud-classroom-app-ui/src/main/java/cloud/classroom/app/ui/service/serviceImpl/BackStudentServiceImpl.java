package cloud.classroom.app.ui.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cloud.classroom.app.ui.service.BackStudentService;
import cloud.entity.classroom.every.User;

@Service
public class BackStudentServiceImpl implements BackStudentService {
	
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	
	
	@HystrixCommand(fallbackMethod = "fallbackInsertStudent")
	@Override
	public boolean insertStudent(User user)
	{
	
		String result = restTemplate.postForObject("http://"+SERVICE_NAME +"/backuser/adduser", user, String.class);
		if(result.equals("success"))
		{
		  return true;
		}else{
			return false;
		 }
	  }

	public boolean fallbackInsertStudent(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}

	
	@HystrixCommand(fallbackMethod = "fallbackUpdateStudent")
	@Override
	public boolean updateStudent(User user)
	{
	
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/teacher/updatestudent", user, String.class);

		if (result.equals("success"))
		{ return true;
		}else{
		  return false;
		}
	}

	public boolean fallbackUpdateStudent(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackDeleteStudent")
	@Override
	public boolean deleteStudent(Integer id)
	{
	
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/teacher/updatestudent", id, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackDeleteStudent(Integer id)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}
	
	
	
}
