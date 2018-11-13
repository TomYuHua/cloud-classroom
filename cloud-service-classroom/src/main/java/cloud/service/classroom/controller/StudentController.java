package cloud.service.classroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.StudentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
@Controller
@RequestMapping("/student")
public class StudentController
{

	@Autowired
	private StudentService studentService;

	@ApiOperation(value = "保存学生信息", notes = "保存学生的个人信息")
	@ApiImplicitParam(name = "student", value = "保存学生的信息", required = true, dataType = "Student")
	@RequestMapping(value = "/insertStudent", method = RequestMethod.POST)
	@ResponseBody
	public String saveStudent(@RequestBody User user)
	{
		if(studentService.insertStudent(user)){
			return "success";
		} else{
			return "fail";
		}
		

	}

	@ApiOperation(value = "展示学生个人信息", notes = "展示学生的个人信息")
	@ApiImplicitParam(name = "id", value = "展示学生个人的信息", required = true, dataType = "Integer")
	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
	public User getStudent(@PathVariable Integer id) throws Exception
	{
		User user = studentService.getStudent(id);
		return user;

	}

/*	@ApiOperation(value = "修改学生信息", notes = "修改学生的个人信息")
	@ApiImplicitParam(name = "student", value = "修改老师个人的信息", required = true, dataType = "Teacher")
	@RequestMapping(value = "/student/student", method = RequestMethod.PUT)
	public String updateTeacher(@RequestBody User user)
	{

		if (studentService.updateStudent(user));

		return "success";

	}*/

	@ApiOperation(value = "删除学生信息", notes = "删除学生个人信息")
	@ApiImplicitParam(name = "id", value = "删除学生个人的信息", required = true, dataType = "Integer")
	@RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
	public String deleteStudent(@PathVariable Integer id)
	{

		if (studentService.deleteStudent(id))
		{
			return "success";
		} else
		{
			return "fail";
		}
	}

}
