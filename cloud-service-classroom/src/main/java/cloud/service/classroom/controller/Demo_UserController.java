package cloud.service.classroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.entity.classroom.user.Users;
import cloud.service.classroom.interfaces.Demo_UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/demouser")
public class Demo_UserController
{
	@Autowired
	private Demo_UserService userService;

	@RequestMapping("/insert")
	public int insert(Users users)
	{
		// Users users = new Users();
		// users.setUsername("testName");
		// users.setEmail("testEmail");
		return userService.insert(users);
	}

	@RequestMapping("/selectAll")
	public List<Users> selectAll()
	{
		return userService.selectAll();
	}

	@RequestMapping("/user")
	public String getStudents()
	{
		return "nishiniu";
	}

	@ApiOperation(value = "鑾峰彇涓暟", notes = "鑾峰彇鎬绘暟淇℃伅")
	@ApiImplicitParam(name = "getcount", value = "鑾峰彇涓暟", required = true)
	@RequestMapping("/getcount")
	public Integer getinfo()
	{
		return userService.getcount();
	}

	
}
