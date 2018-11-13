package cloud.service.classroom.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.BackUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/backuser")
public class BackUserController
{

	@Autowired
	private BackUserService backUserService;

	@ApiOperation(value = "新增用户信息", notes = "新增用户信息")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestBody User user)
	{

		if (backUserService.addUser(user))
			;

		return "success";
	}

	@ApiOperation(value = "新增用户信息", notes = "新增用户信息")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/binduser", method = RequestMethod.POST)
	@ResponseBody
	public String bindUser(@RequestBody User user)
	{

		if (backUserService.bindUser(user));

		return "success";
	}

	@ApiOperation(value = "批量改变用户角色", notes = "批量改变用户角色")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "items", dataType = "String", required = true, value = "用户id的拼接"),
			@ApiImplicitParam(paramType = "query", name = "userRoleId", dataType = "Integer", required = true, value = "用户应绑定Id"),
			@ApiImplicitParam(paramType = "query", name = "createUserId", dataType = "Integer", required = true, value = "操作用户的id") })
	@RequestMapping(value = "/changeUserRole", method = RequestMethod.GET)
	@ResponseBody
	public String changeUserRole(String items, Integer userRoleId, Integer createUserId)
	{

		if(backUserService.changeUserRole(items, userRoleId, createUserId));

		return "success";
	}

}
