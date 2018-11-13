package cloud.classroom.app.ui.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cloud.classroom.app.ui.service.UserService;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.every.User;

@Service
public class UserServiceImpl implements UserService
{

	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;

	@HystrixCommand(fallbackMethod = "fallbackGetUserByUserName")
	@Override
	public User getUserByUserName(String userName)
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getUserByUserName?userName={userName}", User.class, userName);

	}

	public User fallbackGetUserByUserName(String userName)
	{

		System.out.println("HystrixCommand fallbackMethod handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackInsertUser")
	@Override
	public boolean insertUser(User user)
	{

		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/insertUser", user, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackInsertUser(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackUpdateUser")
	@Override
	public boolean updateUser(User user)
	{
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/updateUser", user, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackUpdateUser(User user)
	{
		System.out.println("HystrixCommand fallbackUpdateUserMethod handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackResetPassWord")
	@Override
	public boolean resetPassWord(User user)
	{
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/resetPassWord", user, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackResetPassWord(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackUserResetPassWord")
	@Override
	public boolean userResetPassWord(User user)
	{
		String result = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/userResetPassWord", user, String.class);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackUserResetPassWord(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetUserDetail")
	@Override
	public User getUserDetail(Integer userId, Integer userType)
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getUserDetail?userId={userId}&userType={userType}", User.class, userId,
				userType);
	}

	public User fallbackGetUserDetail(Integer userId, Integer userType)
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getUserDetail?userId={userId}&userType={userType}", User.class, userId,
				userType);
	}
	/*
	 * private User fallbackGetUserById(Integer id) { User user = new User();
	 * System.out.println("HystrixCommand fallbackMethod handle!");
	 * 
	 * return user; }
	 */

	/*
	 * @HystrixCommand(fallbackMethod = "fallbackDeleteUser") public boolean
	 * deleteUser(Integer id) { String result =
	 * restTemplate.postForObject("http://" + SERVICE_NAME + "/user/deleteUser",
	 * id, String.class);
	 * 
	 * if (result.equals("success")) { return true; } else { return false; } }
	 */

	@HystrixCommand(fallbackMethod = "fallbackDeleteUser")
	@Override
	public boolean deleteUser(String items)
	{
		String result = restTemplate.getForObject("http://" + SERVICE_NAME + "/user/deleteUser?items={items}", String.class, items);

		if (result.equals("success"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean fallbackDeleteUser(String items)
	{

		System.out.println("HystrixCommand fallbackMethod handle!");

		return false;
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

	@HystrixCommand(fallbackMethod = "fallbackGetUserPage")
	@Override
	public JSONObject getUserPage(User user)
	{
		Page<User> users = new Page<User>();
		JSONObject jSONObject = restTemplate.postForObject("http://" + SERVICE_NAME + "/user/getUserByPage", user, JSONObject.class);
		return jSONObject;
	}

	public JSONObject fallbackGetUserPage(User user)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetFamousTeacher")
	public List<User> getFamousTeacher(Integer m, Integer n)
	{
		List<User> list = Arrays
				.asList(restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getFamousTeacher?m={m}&n={n}", User[].class, m, n));

		return list;
	}

	public List<User> fallbackGetFamousTeacher(Integer m, Integer n)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetTeacherInfro")
	@Override
	public UserVo getTeacherInfro(Integer userId)
	{
		UserVo userVo = restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getTeacherInfro?userId={userId}", UserVo.class, userId);
	
		return userVo;
	}

	public UserVo fallbackGetTeacherInfro(Integer userId)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackGetTeacherInfro")
	@Override
	public User getByNamePassword(String userName, String password)
	{
		User user = restTemplate.getForObject("http://" + SERVICE_NAME + "/user/getByNamePassword?userName={userName}&password={password}",
				User.class, userName, password);
		return user;
	}

	public User fallbackGetByNamePassword(String userName, String password)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackBindType")
	@Override
	public String bindType(User user)
	{
		return restTemplate.postForObject("http://" + SERVICE_NAME + "/user/bindType", user, String.class);
	};

	public String fallbackBindType(User user)
	{
		System.out.println("HystrixCommand fallbackBindType handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackCheckUserType")
	@Override
	public User checkUserType(String userName)
	{
		return restTemplate.getForObject("http://" + SERVICE_NAME + "/user/checkUserType?userName={userName}", User.class, userName);
	}

	public User fallbackCheckUserType(String userName)
	{
		System.out.println("HystrixCommand fallbackBindType handle!");

		return null;
	}

	@HystrixCommand(fallbackMethod = "fallbackIsExistsUser")
	@Override
	public int IsExistsUser(String account)
	{
	
		int i=restTemplate.getForObject("http://" + SERVICE_NAME + "/user/isExistsUser?account={account}", Integer.class, account);
		
		return i;
	}

	public int fallbackIsExistsUser(String account)
	{
		System.out.println("HystrixCommand fallbackBindType handle!");

		return -1;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackSelectScores")
	@Override
	public User selectScores(Integer id,Integer userId){
		User user = restTemplate.getForObject("http://" + SERVICE_NAME + "/user/selectScores?id={id}&userId={userId}",User.class,id,userId);
		return user;
	}
	
	public User fallbackSelectScores(Integer id,Integer userId)
	{
		System.out.println("HystrixCommand fallbackSelectScores handle!");

		return null;
	}


}