package cloud.classroom.app.ui.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cloud.classroom.app.ui.service.BackUserService;
import cloud.entity.classroom.every.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/binduser")
public class UserTypeAuthenticatedController
{         

	@Autowired
	private BackUserService backUserService;
	
	private static final String userInfo = "userInfoId";

	@RequestMapping(value = "/bindType", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject bindType(@RequestBody User user)
	{
		JSONObject map = new JSONObject();
		if (this.backUserService.bindUser(user));
		{
			map.put("result", "success");
		}

		return map;
	}

	@RequestMapping(value = "/bindpage")
	public String bindPage(Model model,HttpServletRequest request)
	{  HttpSession sessions = request.getSession();
	   User user = (User) sessions.getAttribute(userInfo);
	   model.addAttribute("user",user);

	   	return "/Bind/identity";
	}

	@RequestMapping(value = "/bindnext")
	public String bindNext(ModelMap map, HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{

		return "/Bind/identity-next";
	}
	                           
	@RequestMapping(value = "/resultsuccess")
	public String ResultSuccess()
	{

		return "/Bind/identity-adopt";
	}
	
	@RequestMapping(value = "/resultfail")
	public String ResultFail()
	{

		return "/Bind/identity-fail";
	}

}
