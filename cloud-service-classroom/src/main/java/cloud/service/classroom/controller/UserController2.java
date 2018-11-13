package cloud.service.classroom.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.PageInfro;
import cloud.entity.classroom.every.RequestParameter;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.BackUserService;
import cloud.service.classroom.interfaces.UserService2;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UserController2
{

	@Autowired
	private UserService2 userService;
	@Autowired
	private BackUserService backUserService;

	@ApiOperation(value = "用户分页", notes = "通过修改用户的字段值来达到")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = false, dataType = "User")
	@RequestMapping(value = "/getUserByPage", method = RequestMethod.POST) 
	public JSONObject getPage(@RequestBody User user) throws Exception
	{
		Integer page = user.page;
		Integer rows = user.rows;
		Page<User> users = userService.getPage(page, rows, user);

		ResponseInfro information = new ResponseInfro();
		PageInfro pageInfro = new PageInfro();
		pageInfro.setPageNum(String.valueOf(users.getPageNum()));
		pageInfro.setPages(String.valueOf(users.getPages()));
		pageInfro.setPageSize(String.valueOf(users.getPageSize()));
		pageInfro.setTotal(String.valueOf(users.getTotal()));
		information.dataPage = users.getResult();
		information.pageInfro = pageInfro;
		information.error = "0";

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("information", information);

		return jsonObj;
	}

	@ApiOperation(value = "用户审核", notes = "通过修改用户的属性来达到")
	@ApiImplicitParam(name = "requst", value = "请求参数", required = true, dataType = "RequstParameter")
	@RequestMapping(value = "/auditUser", method = RequestMethod.POST)
	public String auditUser(@RequestBody RequestParameter request)
	{
		String items = request.getItems();
		String[] item = items.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < item.length; i++)
		{
			list.add(new Integer(item[i]));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("list", list);
		params.put("type", request.getType());
		if (userService.auditUser(params))
		{
			return "success";
		} else
		{
			return "fail";
		}

	}

	@ApiOperation(value = "用户角色设置", notes = "通过修改用户的角色来达到角色设置")
	@ApiImplicitParam(name = "request", value = "请求参数", required = true, dataType = "RequestParameter")
	@RequestMapping(value = "/userRole/{id}", method = RequestMethod.PUT)
	public String makeRole(@RequestBody RequestParameter request)
	{
		Integer id = request.getId();
		Integer type = Integer.valueOf(request.getType());
		if (userService.makeRole(id, type))
		{
			return "success";
		} else
		{
			return "fail";
		}

	}

	@ApiOperation(value = "新增用户信息", notes = "新增用户信息")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public String insertUser(@RequestBody User user)
	{
		// HttpSession session=request.getSession();
		// String code=(String)session.getAttribute("confirmCode");
		// String inputPhone=student.getTel();
		// if(phone.equals(inputPhone) && code.equals(inputCode) ){

		if (userService.insertUser(user))
			;

		return "success";
	}

	@ApiOperation(value = "用户登录", notes = "以实体类为对象用户个人登录")
	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户实体类", required = true, dataType = "String")
	@RequestMapping(value = "/getUserByUserName", method = RequestMethod.GET)
	public User loginUser(String userName)
	{
		User user = this.userService.getUserByUserName(userName);

		return user;

	}

	@ApiOperation(value = "用户重置密码", notes = "用户重置密码")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/resetPassWord", method = RequestMethod.POST)
	public String resetPassWord(@RequestBody User user)
	{

		if (this.userService.resetPassWord(user));

		return "success";

	}

	@ApiOperation(value = "用户修改密码", notes = "用户修改密码")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = false, dataType = "User")
	@RequestMapping(value = "/userResetPassWord", method = RequestMethod.POST)
	public String userResetPassWord(@RequestBody User user)
	{

		if (this.userService.userResetPassWord(user))
		{

			return "success";
		} else
		{
			return "fail";
		}
	}

	@ApiOperation(value = "得到用户信息", notes = "得到用户信息")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/getUserByInfor", method = RequestMethod.POST)
	public User getUserByInfor(@RequestBody User user) throws Exception
	{
		String a = "c";
		User person = this.userService.getUser(user);
		return person;

	}

	@ApiOperation(value = "用户修改账号", notes = "用户修改账号")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@RequestBody User user)
	{
		if (this.userService.updateUser(user))
			;

		return "success";

	}

	@ApiOperation(value = "检查用户是否存在", notes = "检查用户是否存在")
	@ApiImplicitParam(name = "email", value = "用户email", required = true, dataType = "String")
	@RequestMapping(value = "/checkExistMail", method = RequestMethod.GET)
	public User checkExistMail(String email) throws Exception
	{
		return this.userService.checkExistMail(email);

	}

	@ApiOperation(value = "用户删除账号", notes = "用户删除账号")
	@ApiImplicitParam(paramType = "query", name = "items", value = "用户id组", required = true, dataType = "String")
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(String items)
	{
		String[] item = items.split(",");
		boolean result = true;
		for (int i = 0; i < item.length; i++)
		{
			if (result)
				;
			result = userService.deleteUser(Integer.parseInt(item[i]));
		}
		return "success";
	}

	@ApiOperation(value = "名师推荐", notes = "名师推荐")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType = "query", name = "m", dataType = "Integer", required = true, value = "获取记录位置"),
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "获取总条数") })
	@RequestMapping(value = "/getFamousTeacher", method = RequestMethod.GET)
	public List<User> getFamousTeacher(Integer m, Integer n)
	{
		List<User> list = userService.getFamousTeacher(m, n);
		String a = "c";
		return list;

	}

	@ApiOperation(value = "老师记录数等信息", notes = "老师记录数等信息")
	@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id", defaultValue = "5")
	@RequestMapping(value = "/getTeacherInfro", method = RequestMethod.GET)
	public UserVo getTeacherInfro(Integer userId)
	{
		UserVo userVo = userService.getTeacherInfro(userId);
		return userVo;

	}

	@ApiOperation(value = "用户详细信息", notes = "用户详细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户Id", defaultValue = "5"),
			@ApiImplicitParam(paramType = "query", name = "userType", dataType = "Integer", required = true, value = "用户类型", defaultValue = "1") })
	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
	public User getUserDetail(Integer userId, Integer userType) throws Exception
	{
		User user = new User();
		user.setUserId(userId);
		user.setUserType(userType);
		User person = this.userService.getUser(user);
		return person;

	}

	@ApiOperation(value = "查找除普通用户以外的角色", notes = "查找除普通用户以外的角色")
	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户账号", required = true, dataType = "String")
	@RequestMapping(value = "/selectTypes", method = RequestMethod.GET)
	public Integer selectTypes(String userName)
	{

		Integer c = userService.selectTypes(userName);
		return c;
	}

	@ApiOperation(value = "用户详细信息", notes = "用户详细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userName", dataType = "String", required = true, value = "用户名词", defaultValue = "username"),
			@ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "用户密码", defaultValue = "password") })
	@RequestMapping(value = "/getByNamePassword", method = RequestMethod.GET)
	public User getByNamePassword(String userName, String password) throws Exception
	{

		return userService.getByNamePassword(userName, password);

	}

	@ApiOperation(value = "查找除普通用户以外的用户", notes = "查找除普通用户以外的用户")
	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户账号", required = true, dataType = "String")
	@RequestMapping(value = "/checkUserType", method = RequestMethod.GET)
	public User checkUserType(String userName)
	{
		User user = userService.checkUserType(userName);
		return user;
	}

	@ApiOperation(value = "查找用户是否存在", notes = "查找用户是否存在")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "account", dataType = "String", required = true, value = "用户账号", defaultValue = "nishizhu") })
	@RequestMapping(value = "/isExistsUser", method = RequestMethod.GET)
	public Integer IsExistsUser(String account)
	{
		return userService.IsExistsUser(account);

	}

	@ApiOperation(value = "查找用户为某资源评分的分数", notes = "查找用户为某资源评分的分数")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "资源id"),
			@ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户id") })
	@RequestMapping(value = "/selectScores", method = RequestMethod.GET)
	public User selectScores(Integer id, Integer userId)
	{
		return userService.selectScores(id, userId);

	}
}
