package cloud.classroom.app.ui.AdminBackend.Controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;

import cloud.classroom.app.ui.Util.MD5Util;
import cloud.classroom.app.ui.annotation.Permission;
import cloud.classroom.app.ui.service.BackStudentService;
import cloud.classroom.app.ui.service.BackTeacherService;
import cloud.classroom.app.ui.service.BackUserService;
import cloud.classroom.app.ui.service.UserService;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.FileHelper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.Enum.RoleId;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.Student;
import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;

@Permission
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value = "/backuser")
public class BackUserController
{
	private static Logger log = LoggerFactory.getLogger(BackUserController.class);

	@Value("${dfs-fdfs-file-api}")
	private String dfsFdfsFileApi;

	@Value("${dfs-filesystem}")
	private String filesystem;

	@Autowired
	private BackStudentService backStudnetService;

	@Autowired
	private BackTeacherService backTeacherService;

	@Autowired
	private BackUserService backUserService;

	@Autowired
	private UserService userService;

	private static final String userInfo = "userInfoId";

	@RequestMapping("/users")
	public ModelAndView index()
	{
		return new ModelAndView("/AdminBackend/users");
		// return "/AdminBackend/users";
	}

	@RequestMapping(value = "/addUser")
	@ResponseBody
	public JSONObject addUser(@RequestParam(required = false) String userType, @RequestParam(required = false) String userName,
			@RequestParam(required = false) String sex, @RequestParam(required = false) String email, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String nickName, @RequestParam(required = false) String name,
			@RequestParam(required = false) String idNumber, @RequestParam(required = false) String studentNo,
			@RequestParam(required = false) String className, @RequestParam(required = false) String gradeName,
			@RequestParam(required = false) String highDegree, @RequestParam(required = false) String birthday,
			@RequestParam(required = false) String teacherIntroduction, @RequestParam(required = false) String province,
			@RequestParam(required = false) String city, @RequestParam(required = false) String area, @RequestParam(required = false) String addr,
			@RequestParam(required = false) String description, @RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String jobNum, @RequestParam(required = false) String jobTitle) throws Exception
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(birthday);
			User user = new User();
			String fileName = file.getOriginalFilename();
			if (!"".equals(fileName))
			{
				byte[] a = file.getBytes();
				Map<String, String> paramMap = Maps.newHashMap();
				paramMap.put("fileStr", Base64Helper.encode(a));
				paramMap.put("fileExtName", FileHelper.getExtension(fileName, true));
				paramMap.put("fileSize", String.valueOf(a.length));
				String url = dfsFdfsFileApi + "/upload";
				String result = HttpHelper.URLPost(url, paramMap, "utf-8");
				int code = MessageNotifyUtil.parse(result).getCode();
				if (code == 200)
				{
					Map<String, Object> map = MessageNotifyUtil.parse(result).getAppendMsg();
					String field = (String) map.get("fileid");
					user.setImgSrc(field);
				}
			}
			;
			String pwd = MD5Util.getEncryptedPwd("123456");
			user.setPassWord(pwd);
			user.setUserType(Integer.valueOf(userType));
			user.setState(1);
			if (name != null)
			{
				user.setName(name);
			}
			if (userName != null)
			{
				user.setUserName(userName);
			}
			if (nickName != null)
			{
				user.setNickName(nickName);
			}
			if (phone != null)
			{
				user.setPhone(phone);
			}
			if (sex != null)
			{
				user.setSex(Integer.valueOf(sex));
			}
			if (email != null)
			{
				user.setEmail(email);
			}
			if (userType.equals("1"))
			{
				Student student = new Student();
				if (idNumber != null)
				{
					student.setIdNumber(idNumber);
				}
				if (studentNo != null)
				{
					student.setStudentNo(studentNo);
				}
				if (className != null)
				{
					student.setClassName(className);
				}
				if (gradeName != null)
				{
					student.setGradeName(gradeName);
				}
				if (birthday != null)
				{
					student.setBirthday(date);
				}
				if (description != null)
				{
					student.setDescription(description);
				}
				if (province != null)
				{
					student.setProvince(province);
				}
				if (city != null)
				{
					student.setCity(city);
				}
				if (area != null)
				{
					student.setArea(area);
				}
				if (addr != null)
				{
					student.setAddr(addr);
				}
				user.setStudent(student);
				if (this.backStudnetService.insertStudent(user))
				{
					jsonObj.put("result", "success");
				}
				;
			} else if (userType.equals("2"))
			{
				Teacher teacher = new Teacher();
				if (idNumber != null)
				{
					teacher.setIdNumber(idNumber);
				}
				if (jobNum != null)
				{
					teacher.setJobNum(jobNum);
				}
				if (jobTitle != null)
				{
					teacher.setJobTitle(jobTitle);
				}
				if (birthday != null)
				{
					teacher.setBirthday(date);
				}
				if (province != null)
				{
					teacher.setProvince(province);
				}
				if (city != null)
				{
					teacher.setCity(city);
				}
				if (area != null)
				{
					teacher.setArea(area);
				}
				if (addr != null)
				{
					teacher.setAddr(addr);
				}
				if (teacherIntroduction != null)
				{
					teacher.setTeacherIntroduction(teacherIntroduction);
				}
				user.setTeacher(teacher);
				if (this.backTeacherService.insertTeacher(user))
				{
					jsonObj.put("result", "success");
				}
				;
			}

		} catch (Exception e)
		{

			log.error("添加失败 ", e);
		}

		return jsonObj;
	}

	@RequestMapping(value = "/updateUser", method = { RequestMethod.PUT })
	@ResponseBody
	public JSONObject updateUser(@RequestBody User user) throws Exception
	{
		JSONObject jsonObject = new JSONObject();
		try
		{
			if ("1".equals(user.getUserType()))
			{
				if (this.backStudnetService.updateStudent(user))
				{
					jsonObject.put("result", "success");
				}
				;
			}
			if ("2".equals(user.getUserType()))
			{
				if (this.backTeacherService.updateTeacher(user))
				{
					jsonObject.put("result", "success");
				}
				;
			}

		} catch (Exception e)
		{

			log.error("更新失败 ", e);
		}

		return jsonObject;
	}

	@RequestMapping(value = "/deleteUser", method = { RequestMethod.DELETE })
	@ResponseBody
	public JSONObject deleteUser(@RequestBody User user) throws Exception
	{

		JSONObject jsonObj = new JSONObject();
		try
		{
			if ("1".equals(user.getUserType()))
			{
				if (this.backStudnetService.deleteStudent(user.getUserId()))
				{
					jsonObj.put("result", "success");
				}
				;
			}
			if ("2".equals(user.getUserType()))
			{
				if (this.backTeacherService.deleteTeacher(user.getUserId()))
				{
					jsonObj.put("result", "success");
				}
				;
			}

		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return jsonObj;
	}

	@RequestMapping(value = "/userAdmin")
	public String userAdmin()
	{
		return "users";
	}

	@RequestMapping(value = "/getuserpage")
	@ResponseBody
	public JSONObject getUserPage(@RequestParam(required = false) String userType, @RequestParam(required = false) String userName,
			@RequestParam(required = false) String name, @RequestParam(required = false) String sex, @RequestParam(required = false) String state,
			@RequestParam(required = false) String email, @RequestParam(defaultValue = "1") String page,
			@RequestParam(defaultValue = "10") String rows)
	{
		User user = new User();
		if (userType != null)
		{
			user.setUserType(Integer.valueOf(userType));
		}
		if (userName != null)
		{
			user.setUserName(userName);
		}
		if (name != null)
		{
			user.setName(name);
		}
		if (email != null)
		{
			user.setEmail(email);
		}
		if (sex != null)
		{
			user.setSex(Integer.valueOf(sex));
		}
		if (state != null)
		{
			user.setState(Integer.valueOf(state));
		}
		user.setPage(Integer.valueOf(page));
		user.setRows(Integer.valueOf(rows));

		JSONObject k = userService.getUserPage(user);
		k.put("filesystem", filesystem);
		return k;

	}

	@RequestMapping(value = "/deleteuser")
	@ResponseBody
	public JSONObject deleUser(@RequestParam(required = false, name = "items") String items)
	{
		JSONObject jsonObj = new JSONObject();

		Map<String, String> map = new HashMap<String, String>();
		try
		{
			if (this.userService.deleteUser(items))
			{
				jsonObj.put("result", "success");
			}
			;

		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return jsonObj;
	}

	@RequestMapping(value = "/audituser")
	@ResponseBody
	public JSONObject auditUser(@RequestParam String items, @RequestParam String type)
	{
		JSONObject map = new JSONObject();
		try
		{
			if (backUserService.auditUser(items, type))
			{
				map.put("result", "success");
			} else
			{
				map.put("result", "fail");
			}
			;

		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return map;
	}

	@RequestMapping(value = "/makeRole")
	@ResponseBody
	public JSONObject makeRole(@RequestParam(defaultValue = "1") Integer id, @RequestParam(defaultValue = "1") String type)
	{
		JSONObject map = new JSONObject();
		if (backUserService.makeRole(id, type))
		{
			map.put("result", "success");
		} else
		{
			map.put("result", "fail");
		}
		return map;
	}

	@RequestMapping(value = "/selectTypes")
	@ResponseBody
	public JSONObject selectTypes(@RequestParam(required = false) String userName)
	{
		JSONObject map = new JSONObject();

		Integer userType = backUserService.selectTypes(userName);

		if (userType == null)
		{

			map.put("result", "success");
		} else
		{
			map.put("result", "fail");
		}
		return map;
	}

	@RequestMapping(value = "/resetPassword")
	@ResponseBody
	public JSONObject resetPassword(@RequestParam String userName) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		JSONObject map = new JSONObject();
		User user = new User();
		try
		{
			String pwd = MD5Util.getEncryptedPwd("123456");
			user.setInputPassWord(pwd);
			user.setUserName(userName);
			if (this.userService.userResetPassWord(user))
			{
				map.put("result", "success");
			} else
			{
				map.put("result", "fail");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/auditInfor")
	@ResponseBody
	public User auditInfor(Integer userId, Integer userType)
	{
		User user = new User();

		try
		{
			user = userService.getUserDetail(userId, userType);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return user;
	}

	@RequestMapping(value = "/changeUserRole")
	@ResponseBody
	public JSONObject changeUserRole(@RequestParam String items, @RequestParam String userRoleId, HttpServletRequest request)
	{
		JSONObject map = new JSONObject();
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
      try{
			if (backUserService.changeUserRole(items, Integer.valueOf(userRoleId), user.getUserId()))
			{
				map.put("result", "success");
			} else
			{
				map.put("result", "fail");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return map;
	}
}
