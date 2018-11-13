package cloud.classroom.app.ui.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import cloud.classroom.app.ui.Util.MD5Util;
import cloud.classroom.app.ui.exception.RestFullException;
import cloud.classroom.app.ui.service.BackUserService;
import cloud.classroom.app.ui.service.UserService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.common.helper.Base64Helper;
import cloud.common.helper.FileHelper;
import cloud.common.helper.HttpHelper;
import cloud.common.util.MessageNotifyUtil;
import cloud.entity.classroom.DTO.UserVo;
import cloud.entity.classroom.every.ResponseInfro;
import cloud.entity.classroom.every.Student;
import cloud.entity.classroom.every.Teacher;
import cloud.entity.classroom.every.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value = "/userui")
public class UserController
{

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BackUserService backUserService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${dfs-fdfs-file-api}")
	private String dfsFdfsFileApi;

	@Value("${dfs-filesystem}")
	private String filesystem;
	@Value("${spring.mail.host}")
	private String mailHost;
	@Value("${spring.mail.port}")
	private String mailPort;
	@Value("${spring.mail.username}")
	private String mailUsername;
	@Value("${spring.mail.password}")
	private String mailPassword;

	private static final String userInfo = "userInfoId";

	@RequestMapping("/login")
	@ResponseBody
	public Map<String, String> login(@RequestBody User user, String remember, Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response)
	{
		Map<String, String> map = new HashMap<String, String>();
		User userEntify = new User();
		boolean validResult = false;
		try
		{
			userEntify = userService.getUserByUserName(user.getUserName());
			validResult = MD5Util.validPassword(user.getPassWord(), userEntify.getPassWord());

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
		{
			log.error("", e);
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (userInfo != null && validResult == true)
		{
			/*
			 * if (userEntify.getState() == 1) {
			 */
			if ("yes".equals(remember))
			{
				Cookie cookie = new Cookie("userCookie", userEntify.getName() + "," + userEntify.getPassWord());
				cookie.setMaxAge(60 * 60 * 24 * 14);// 保存两周
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			session.setAttribute(userInfo, userEntify);
			map.put("result", "1");

		} else
		{
			map.put("result", "用户名或密码错误");
		}
		return map;
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{

		if (session != null)
		{
			session.removeAttribute(userInfo);
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if ("userCookie".equals(cookie.getName()))
				{
					Cookie cookie2 = new Cookie("userCookie", null);
					cookie2.setMaxAge(0);
					cookie2.setPath("/");
					response.addCookie(cookie2);
					break;
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/IsExistsUser")
	@ResponseBody
	public JSONObject IsExistsUser(String account)
	{
		JSONObject map = new JSONObject();
		try
		{
			int result = userService.IsExistsUser(account);

			if (result != -1)
			{
				if (result > 0)
				{
					map.put("result", "success");
				} else
				{
					map.put("result", "fail");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("", e);
		}
		return map;
	}

	@RequestMapping(value = "/insertUser", method = { RequestMethod.POST })
	public @ResponseBody Map<String, String> insertUser(@RequestBody User user, HttpServletRequest request) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			String pwd = MD5Util.getEncryptedPwd(user.getPassWord());
			user.setPassWord(pwd);
			user.setState(1);
			HttpSession session = request.getSession();
			String code = (String) session.getAttribute("checkCode");
			String inputEmail = (String) session.getAttribute("email");

			if (inputEmail.equals(user.getEmail()) && code.equals(user.getInputCode()))
			{

				if (this.userService.insertUser(user))
				{
					map.put("result", "success");
				}
			} else
			{
				map.put("result", "fail");
			}
		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return map;
	}

	@RequestMapping(value = "/registpage")
	public String registPage() throws RestFullException
	{

		return "/regist";
	}

	@RequestMapping(value = "/loginPage")
	public String loginPage() throws RestFullException
	{

		return "/login";
	}

	@RequestMapping(value = "/ChangePassWord/{userName:.+}")
	public String changePassWord(@PathVariable("userName") String userName, Model model)
	{

		model.addAttribute("userName", userName);
		return "/ChangePassWord";
	}

	@RequestMapping(value = "/Collection")
	public String collection(Model model)
	{

		// model.addAttribute("user", user);
		return "/Collection";
	}

	@RequestMapping(value = "/forget")
	public String forget()
	{

		// model.addAttribute("user", user);
		return "/forget";
	}

	@RequestMapping("/forgetNext/{userName:.+}")
	public String forgetNext(@PathVariable("userName") String userName, Model model)
	{

		model.addAttribute("userName", userName);
		String a = "c";
		return "/forget-next";
	}

	@RequestMapping(value = "/personal")
	public String personal(Model model)
	{

		User user = new User();
		user.setUserName("123");
		user.setPassWord("2323");
		model.addAttribute("user", user);
		return "PersonalInformation";
	}

	@RequestMapping(value = "/userlogin")
	public String UserLogin(Model model, String userName)
	{

		// model.addAttribute("user", user);
		return "/Index";
	}

	@RequestMapping(value = "/resetPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> resetPassWord(@RequestBody User user) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			String inputPassWord = user.getInputPassWord();
			String pwd = MD5Util.getEncryptedPwd(inputPassWord);
			user.setInputPassWord(pwd);

			if (this.userService.resetPassWord(user))
			{
				map.put("result", "success");
			}
			;

		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return map;
	}

	@RequestMapping(value = "/userResetPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> userResetPassWord(@RequestBody User user) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		User userEntify = new User();
		try
		{
			userEntify = userService.getUserByUserName(user.getUserName());
			boolean validResult = MD5Util.validPassword(user.getPassWord(), userEntify.getPassWord());
			if (validResult)
			{
				String inputPassWord = user.getInputPassWord();
				String pwd = MD5Util.getEncryptedPwd(inputPassWord);
				user.setInputPassWord(pwd);
				if (this.userService.userResetPassWord(user))
				{
					map.put("result", "success");
				}
			} else
			{
				map.put("result", "fail");
			}

		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return map;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> updateUser(@RequestBody User user) throws Exception
	{

		Map<String, String> map = new HashMap<String, String>();
		try
		{
			if (this.userService.updateUser(user))
			{
				map.put("result", "success");
			}

		} catch (Exception e)
		{

			log.error("注册失败 ", e);
		}

		return map;
	}

	public static String getCheckCode()
	{

		String code = "0123456789";
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++)
		{
			sb.append(code.charAt(r.nextInt(code.length())));
		}

		return sb.toString();
	}

	@RequestMapping("/sendemail")
	@ResponseBody
	public Map<String, String> sendEmail(@RequestParam(required = false) String email, HttpServletRequest request)
	{

		Map<String, String> map = new HashMap<String, String>();
		try
		{
			// 创建Properties 类用于记录邮箱的一些属性
			Properties props = new Properties();
			// 表示SMTP发送邮件，必须进行身份验证
			props.put("mail.smtp.auth", "true");
			// 此处填写SMTP服务器
			props.put("mail.smtp.host", mailHost.trim());
			// 端口号，QQ邮箱给出了两个端口
			props.put("mail.smtp.port", mailPort.trim());
			// 此处填写你的账号
			props.put("mail.user", mailUsername.trim());
			// 此处的密码就是16位STMP口令
			props.put("mail.password", mailPassword.trim());

			// 构建授权信息，用于进行SMTP进行身份验证
			Authenticator authenticator = new Authenticator()
			{

				protected PasswordAuthentication getPasswordAuthentication()
				{
					// 用户名、密码
					String userName = props.getProperty("mail.user");
					String password = props.getProperty("mail.password");
					return new PasswordAuthentication(userName, password);
				}
			};
			// 使用环境属性和授权信息，创建邮件会话
			Session mailSession = Session.getInstance(props, authenticator);
			// 创建邮件消息
			MimeMessage message = new MimeMessage(mailSession);
			// 设置发件人
			InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
			message.setFrom(form);

			// 设置收件人的邮箱
			InternetAddress to = new InternetAddress(email.trim());
			message.setRecipient(RecipientType.TO, to);

			// 设置邮件标题
			message.setSubject("获取验证码邮件");

			String checkCode = UserController.getCheckCode();

			HttpSession session = request.getSession();

			session.setAttribute("checkCode", checkCode);

			session.setAttribute("email", email);
			// 设置邮件的内容体
			// message.setContent("这是一封测试邮件", "text/html;charset=UTF-8");
			message.setContent("尊敬的用户，您的验证码为" + checkCode, "text/html;charset=UTF-8");

			// 最后当然就是发送邮件啦
			Transport.send(message);

			map.put("result", "success");
			// // User user = userService.checkExistMail(email);
			// // if (null != user)
			// // {
			// String chekCode = UserController.getCheckCode();
			// final MimeMessage mimeMessage =
			// this.mailSender.createMimeMessage();
			// final MimeMessageHelper messages = new
			// MimeMessageHelper(mimeMessage);
			// messages.setFrom("Tomcoder@qq.com");
			// messages.setTo(email);
			// messages.setSubject("密码重置验证码");
			// messages.setText("尊敬的用户，您的验证码为" + chekCode);
			// this.mailSender.send(mimeMessage);
			// HttpSession session = request.getSession();
			// session.setAttribute("chekCode", chekCode);
			// map.put("result", "success");
			//
			// // } else
			// // {
			// // map.put("result", "fail");
			// // }
		} catch (Exception ex)
		{
			ex.printStackTrace();
			map.put("result", "fail");
		}
		return map;
	}

	@RequestMapping("/turnToNext")
	@ResponseBody
	public Map<String, String> turnToNext(@RequestParam String email, @RequestParam String code, HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		User user = userService.checkExistMail(email);
		if (null != user)
		{
			HttpSession session = request.getSession();
			String checkCode = (String) session.getAttribute("checkCode");
			if (checkCode.equals(code))
			{
				map.put("result", "success");
			}

		}
		return map;
	}

	@RequestMapping(value = "/uploadStudent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> pushUserStudent(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(name = "className", required = false) String className,
			@RequestParam(name = "gradeName", required = false) String gradeName, @RequestParam(required = false) String birthday,
			@RequestParam(required = false) String nickName, @RequestParam(required = false) String name, @RequestParam(required = false) String sex,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String email,
			@RequestParam(name = "userName", required = false) String userName, @RequestParam(required = false) String idNumber,
			@RequestParam(required = false) String description, @RequestParam(required = false) String userType,
			@RequestParam(required = false) String id, @RequestParam(required = false) String studentNo,
			@RequestParam(required = false) String province, @RequestParam(required = false) String city, @RequestParam(required = false) String area,
			@RequestParam(required = false) String addr, @RequestParam(required = false) String studentId, HttpServletResponse response)
			throws IOException, ParseException
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		User user = new User();
		user.setUserType(Integer.valueOf(userType));
		user.setUserId(Integer.valueOf(id));
		if (name != null && !name.equals(""))
		{
			user.setName(name);
		}
		if (userName != null && !userName.equals(""))
		{
			user.setUserName(userName);
		}
		if (nickName != null && !nickName.equals(""))
		{
			user.setNickName(nickName);
		}
		if (phone != null && !phone.equals(""))
		{
			user.setPhone(phone);
		}
		if (sex != null && !sex.equals(""))
		{
			user.setSex(Integer.valueOf(sex));
		}
		if (email != null && !email.equals(""))
		{
			user.setEmail(email);
		}
		Student student = new Student();
		student.setId(Integer.valueOf(studentId));
		if (idNumber != null && !idNumber.equals(""))
		{
			student.setIdNumber(idNumber);
		}
		if (studentNo != null && !studentNo.equals(""))
		{
			student.setStudentNo(studentNo);
		}
		if (className != null && !className.equals(""))
		{
			student.setClassName(className);
		}
		if (gradeName != null && !gradeName.equals(""))
		{
			student.setGradeName(gradeName);
		}
		if (birthday != null && !birthday.equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(birthday);
			student.setBirthday(date);
		}
		if (description != null && !description.equals(""))
		{
			student.setDescription(description);
		}
		if (province != null && !province.equals(""))
		{
			student.setProvince(province);
		}
		if (city != null && !city.equals(""))
		{
			student.setCity(city);
		}
		if (area != null && !area.equals(""))
		{
			student.setArea(area);
		}
		if (addr != null && !addr.equals(""))
		{
			student.setAddr(addr);
		}

		user.setStudent(student);
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
		if (userService.updateUser(user))
		{
			resultMap.put("result", "success");
		} else
		{
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	@RequestMapping(value = "/uploadTeacher", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> pushUserTeacher(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam(required = false) String userName, @RequestParam(required = false) String nickName,
			@RequestParam(required = false) String name, @RequestParam(required = false) String sex, @RequestParam(required = false) String phone,
			@RequestParam(required = false) String email, @RequestParam(required = false) String idNumber,
			@RequestParam(required = false) String description, @RequestParam(required = false) String userType,
			@RequestParam(required = false) int id, @RequestParam(required = false) String highDegree,
			@RequestParam(required = false) String birthday, @RequestParam(required = false) String teacherIntroduction,
			@RequestParam(required = false) String province, @RequestParam(required = false) String city, @RequestParam(required = false) String area,
			@RequestParam(required = false) String addr, @RequestParam(required = false) String jobNum,
			@RequestParam(required = false) String jobTitle, @RequestParam(required = false) int teacherId)
	{
		Map<String, String> resultMap = new HashMap<String, String>();

		try
		{

			User user = new User();
			user.setUserType(Integer.valueOf(userType));
			user.setUserId((Integer) id);
			if (userName != null && !userName.equals(""))
			{
				user.setUserName(userName);
			}
			if (name != null && !name.equals(""))
			{
				user.setName(name);
			}
			if (nickName != null && !nickName.equals(""))
			{
				user.setNickName(nickName);
			}
			if (phone != null && !phone.equals(""))
			{
				user.setPhone(phone);
			}
			if (sex != null && !sex.equals(""))
			{
				user.setSex(Integer.valueOf(sex));
			}
			if (email != null && !email.equals(""))
			{
				user.setEmail(email);
			}
			Teacher teacher = new Teacher();
			teacher.setTeacherId((Integer) teacherId);
			if (idNumber != null && !idNumber.equals(""))
			{
				teacher.setIdNumber(idNumber);
			}
			if (highDegree != null && !highDegree.equals(""))
			{
				teacher.setHighDegree(highDegree);
			}
			if (jobNum != null && !jobNum.equals(""))
			{
				teacher.setJobNum(jobNum);
			}
			if (jobTitle != null && !jobTitle.equals(""))
			{
				teacher.setJobTitle(jobTitle);
			}
			if (birthday != null && !birthday.equals(""))
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(birthday);
				teacher.setBirthday(date);
			}
			if (province != null && !province.equals(""))
			{
				teacher.setProvince(province);
			}
			if (city != null && !city.equals(""))
			{
				teacher.setCity(city);
			}
			if (area != null && !area.equals(""))
			{
				teacher.setArea(area);
			}
			if (addr != null && !addr.equals(""))
			{
				teacher.setAddr(addr);
			}
			if (teacherIntroduction != null && !teacherIntroduction.equals(""))
			{
				teacher.setTeacherIntroduction(teacherIntroduction);
			}
			user.setTeacher(teacher);

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
			if (userService.updateUser(user))
			{
				resultMap.put("result", "success");
			} else
			{
				resultMap.put("result", "fail");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultMap;
	}

	@RequestMapping(value = "/getFamousTeacher", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getFamousTeacher(int m, int n)
	{
		List<User> userList = userService.getFamousTeacher((Integer) m, (Integer) n);
		try
		{
			for (User user : userList)
			{
				if (null != user.getImgSrc())
				{
					String imgPath = filesystem + user.getImgSrc();
					user.setImgSrc(imgPath);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("getFamousTeacher", e);
		}
		return userList;
	}

}
