package cloud.service.classroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.TeacherService2;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/teacher")
public class TeacherController
{

	@Autowired
	private TeacherService2 teacherService;

	@ApiOperation(value = "展示老师个人信息", notes = "展示老师的个人信息")
	@ApiImplicitParam(name = "teacher", value = "展示老师个人的信息", required = true, dataType = "Integer")
	@RequestMapping(value = "/teacher/{id}", method = RequestMethod.GET)
	public User getTeacher(@PathVariable Integer id) throws Exception
	{
		User user = teacherService.getTeacher(id);
		return user;

	}

/*	@ApiOperation(value = "修改老师信息", notes = "修改老师个人信息")
	@ApiImplicitParam(name = "updateTeacher", value = "修改老师个人的信息", required = true, dataType = "Teacher")
	@RequestMapping(value = "/teacher/teacher", method = RequestMethod.PUT)
	public String updateTeacher(@RequestBody User user)
	{

		if (teacherService.updateTeacher(user))
			;

		return "success";

	}
*/
	@ApiOperation(value = "删除老师信息", notes = "删除老师个人信息")

	@RequestMapping(value = "/teacher/id")
	public String deleteUser(@PathVariable Integer id)
	{

		teacherService.deleteTeacher(id);

		return "success";

	}
   
	@ApiOperation(value = "保存老师信息", notes = "保存老师的个人信息")
	@ApiImplicitParam(name = "teacher", value = "保存老师的信息", required = true, dataType = "Teacher")
	@RequestMapping(value = "insertStudent", method = RequestMethod.POST)
	public String saveTeacher(@RequestBody User user)
	{
		if(teacherService.insertTeacher(user)){
			return "success";
		} else{
			return "fail";
		}
		

	}
}
