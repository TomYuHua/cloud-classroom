package com.singFly.cloud_examination_appUi.cloud_examination_userController;




import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_appUi.cloud_examination_Util.CommonMailSender;
import com.singFly.cloud_examination_appUi.cloud_examination_Util.MD5Util;
import com.singFly.cloud_examination_appUi.cloud_examination_service.UserService;
import com.singFly.cloud_examination_user.User;







@RequestMapping(value = "/user")
@Controller
public class UserController {
	
	@Value("${dfs-fdfs-file-api}")
	private String dfsFdfsFileApi;

	@Value("${dfs-filesystem}")
	private String filesystem;
	
	private static final String userInfo = "userInfoId";

	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/operateUser")
	@ResponseBody
	public JSONObject operateUser(@RequestBody UserVo user,@RequestBody String operationType) throws Exception
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=userService.operateUser(user);
			if (result)
			{
				jsonObj.put("result", "success");
			} else
			{
				jsonObj.put("result", "fail");
			}
		} catch (Exception e)
		{

			log.error("操作失败 ", e);
		}

		return jsonObj;
	}
	
	@RequestMapping(value = "/deleteUsers")
	@ResponseBody
	public JSONObject deleteUsers(@RequestParam String items) throws Exception
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=userService.deleteUsers(items);
			if (result)
			{
				jsonObj.put("result", "success");
			} else
			{
				jsonObj.put("result", "fail");
			}
		} catch (Exception e)
		{

			log.error("操作失败 ", e);
		}

		return jsonObj;
	}
	
	@RequestMapping(value = "/getUser")
	@ResponseBody
	public User getUser(@RequestParam int id,@RequestParam int type){ 
	    return userService.getUser(id, type);
	}
	
	@RequestMapping(value = "/registUser")
	@ResponseBody
	public JSONObject registUser(@RequestBody UserVo user,HttpServletRequest request){ 
		JSONObject jsonObject = new JSONObject();
		try{
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("checkCode");
		String inputEmail = (String) session.getAttribute("email");

		if (inputEmail.equals(user.getEmail()) && code.equals(user.getInputCode()))
		{

			if (this.userService.operateUser(user))
			{
				jsonObject.put("result", "success");
			}
		} else
		{
			jsonObject.put("result", "fail");
		}
             }catch (Exception e)
	    {

		     log.error("注册失败 ", e);
	       }
		
	        return jsonObject;
	}
	
	@RequestMapping("/sendemail")
	@ResponseBody
	public JSONObject sendEmail(@RequestParam(required = false) String email, HttpServletRequest request)
	{  
		JSONObject jsonObj = new JSONObject();
		try {
			String checkCode = getCheckCode();		
			jsonObj=CommonMailSender.sendEmail(email, checkCode);
			if(jsonObj.get("result")=="success");
			HttpSession session = request.getSession();
			session.setAttribute("checkCode", checkCode);
			session.setAttribute("email", email);
		} catch (Exception e) {
		
		     log.error("操作失败 ", e);
		}

           return jsonObj;
}
	
	 public String getCheckCode()
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
	
	
		@RequestMapping("/login")
		@ResponseBody
		public JSONObject login(@RequestBody UserVo user, String remember, Model model, HttpSession session, HttpServletRequest request,
				HttpServletResponse response)throws Exception
		{
			JSONObject jsonObj = new JSONObject();
			User userEntify = new User();
			boolean validResult = false;
			try
			{
				userEntify = userService.getUser(user.getId(),user.getUserType());
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
				jsonObj.put("result", "success");

			} else
			{
				jsonObj.put("result", "用户名或密码错误");
			}
			return jsonObj;
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
		
		@RequestMapping("/turnToNext")
		@ResponseBody
		public JSONObject turnToNext(@RequestParam String email, @RequestParam String code, HttpServletRequest request)
		{
			JSONObject jsonObj = new JSONObject();
			User user = userService.checkExistMail(email);
			if (null != user)
			{   HttpSession session = request.getSession();
				String checkCode = (String) session.getAttribute("checkCode");
				if (checkCode.equals(code))
				{
					jsonObj.put("result", "success");
				 }

			  }
			         return jsonObj;
		        }
		
		
		@RequestMapping(value = "/resetPassWord", method = RequestMethod.POST)
		@ResponseBody
		public JSONObject resetPassWord(@RequestBody UserVo user) throws Exception
		{
			JSONObject jsonObj = new JSONObject();
			try
			{    boolean validResult=true;
				int type=user.getOperationType();
				if(type==0){
				User userEntify = userService.getUser(user.getId(),user.getUserType());
			    validResult = MD5Util.validPassword(user.getPassWord(), userEntify.getPassWord());}
				if(validResult){
				String inputPassWord = user.getInputPassWord();
				String pwd = MD5Util.getEncryptedPwd(inputPassWord);
				user.setInputPassWord(pwd);

				if (this.userService.resetPassWord(user))
				{
					jsonObj.put("result", "success");
				 }
				};

			} catch (Exception e)
			{

				log.error("操作发生失败 ", e);
			}

			return jsonObj;
		}



	
}
