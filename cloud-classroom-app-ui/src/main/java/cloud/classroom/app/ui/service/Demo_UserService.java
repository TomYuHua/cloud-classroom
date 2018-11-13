package cloud.classroom.app.ui.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cloud.entity.classroom.user.Users;

@Service
public class Demo_UserService
{
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;

	@HystrixCommand(fallbackMethod = "fallbackSearchAll")
	public List<Users> selectAll()
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/selectAll", List.class);
	}

	private String fallbackSearchAll()
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return "HystrixCommand fallbackMethod handle";
	}

	
	@HystrixCommand(fallbackMethod = "fallbackGetCount")
	public int getCount()
	{
		int i=restTemplate.getForEntity("http://" + SERVICE_NAME + "/demouser/getcount", int.class).getBody();
		return i;
	}

	private int fallbackGetCount()
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return -111111111;
	}
	
	// private List<Users> fallbackSearchAll()
	// {
	// System.out.println("HystrixCommand fallbackMethod handle!");
	// List<Users> ls = new ArrayList<Users>();
	// Users user = new Users();
	// user.setUserid(1);
	// user.setUsername("TestHystrixCommand");
	// ls.add(user);
	// return ls;
	// }
}
