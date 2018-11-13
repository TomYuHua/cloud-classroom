package com.singFly.cloud_examination_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_service.interfaces.User.UserOperateService;
import com.singFly.cloud_examination_service.interfaces.User.UserService;
import com.singFly.cloud_examination_user.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;




      
@RequestMapping(value = "/user")
@RestController 
public class UserController {
	
	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	@Qualifier("UserServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("StudentUserImpl")
	private UserService studentService;
	
	
	@Autowired
	@Qualifier("TeacherUserImpl")
	private UserService teacherService;
	
	@Autowired
	private UserOperateService userOperateService;
	
	@ApiOperation(value = "添加或修改用户", notes = "添加或修改用户")
	@ApiImplicitParam(name = "user", dataType = "User", required = true, value = "用户实体类")
	@RequestMapping(value = "/operateUser", method = RequestMethod.POST)
	public boolean operateUser(@RequestBody UserVo user) throws Exception{
		boolean result=false;
		int type=user.getUserType();
		switch(type){
		case 0:
			result=userService.operateUser(user);
		case 1:
		    result=studentService.operateUser(user);
		case 2:
		    result=teacherService.operateUser(user);
		}
	      return result;
	   }
	
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@ApiImplicitParam(name = "items", dataType = "String", required = true, value = "用户信息的拼接")
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public boolean deleteUser(@RequestBody String items){
		boolean result=true;
		String[] item = items.split("|");
		for (int i = 0; i < item.length; i++){
		String[] userInfo=item[i].split(",");
		for (int j = 0; j < 3; i++){   
			if(result){
			String id=userInfo[0];
			String relationId=userInfo[1];
			String type=userInfo[2];
			switch(Integer.valueOf(type)){
			case 0:
				result=userService.deleteUser(Integer.valueOf(id),Integer.valueOf(relationId));
		        break;
			case 1:
				result=studentService.deleteUser(Integer.valueOf(id),Integer.valueOf(relationId));
				break;
			case 2:
				result=teacherService.deleteUser(Integer.valueOf(id),Integer.valueOf(relationId));
				break;
		  } 
			   }
		     }
		   }
                    if(result){
                    return true; 
                      }else{
                    return false;
                   }
	           }
	
	@ApiOperation(value = "获取用户信息详情", notes = "获取用户信息详情")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "用户的id"),
			@ApiImplicitParam(paramType = "query", name = "type", dataType = "Integer", required = true, value = "用户的类型")})
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	       public User getUser(Integer id,Integer type){
	    	    User user=null;
	    	   switch (type) {
			case 0:
				user=userService.getUser(id);
				break;
			case 1:
				user=studentService.getUser(id);
				break;
			case 2:
				user=teacherService.getUser(id);
				break;
               }
	    	   return user;
	       }
	
	@ApiOperation(value = "用户登录", notes = "以实体类为对象用户个人登录")
	@ApiImplicitParam(paramType = "query", name = "userName", value = "用户实体类", required = true, dataType = "String")
	@RequestMapping(value = "/getUserByUserName", method = RequestMethod.GET)
	public User loginUser(String userName)
	{
		User user = userOperateService.getUserByUserName(userName);

		return user;

	}
	@ApiOperation(value = "用户重置密码", notes = "用户重置密码")
	@ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User")
	@RequestMapping(value = "/resetPassWord", method = RequestMethod.POST)
	public boolean resetPassWord(@RequestBody UserVo user)
	{

		return userOperateService.resetPassWord(user);
      }

       
}
