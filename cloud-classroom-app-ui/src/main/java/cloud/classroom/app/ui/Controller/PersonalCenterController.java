package cloud.classroom.app.ui.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cloud.classroom.app.ui.service.BackUserService;
import cloud.classroom.app.ui.service.UserService;
import cloud.classroom.app.ui.service.interfaces.ResourceService;
import cloud.entity.classroom.DTO.DateResourcesVo;
import cloud.entity.classroom.DTO.ResourcesVo;
import cloud.entity.classroom.Resources.Resources;
import cloud.entity.classroom.every.User;

@Controller
@RequestMapping("/personalcenter")
public class PersonalCenterController
{      private static Logger log = LoggerFactory.getLogger(PersonalCenterController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	public ResourceService resourceService;
	@Autowired
	private BackUserService backUserService;
	
 	@Value("${dfs-filesystem}")
	private String filesystem;
 	
	private static final String userInfo = "userInfoId";


	@RequestMapping(value = "/lastlearn")
	public String LastLearn(Model model,HttpServletRequest request)
	{      List<DateResourcesVo> lists=new ArrayList<DateResourcesVo>();
	    try{
		HttpSession sessions= request.getSession();
		User user = (User) sessions.getAttribute(userInfo); 
	    lists=resourceService.getLastLearn(user.getUserId());	
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
 	    model.addAttribute("lists",lists);

	
		return "/PersonalCenter/LastLearn";
	}
	
	@RequestMapping(value = "/getPersonalDownload")
	public String getPersonalDownload(Model model,HttpServletRequest request)
	{       HttpSession sessions= request.getSession();
		User user = (User) sessions.getAttribute(userInfo); 
		List<Resources> lists=resourceService.getPersonalDownload(user.getUserId());	

 	    model.addAttribute("lists",lists);

	
		return "/PersonalCenter/download";
	}
     
	@RequestMapping(value = "/getPersonlCoursePage")
	public String getPersonlCoursePage()
	{      
	
		return "/PersonalCenter/personalcourse";
	} 
	
	
	@RequestMapping(value = "/getPersonlCourse")
	public String getPersonlCourse(@RequestParam int types,Model model,HttpServletRequest request)
	{
		HttpSession sessions = request.getSession();
		User user = (User) sessions.getAttribute(userInfo);
		try
		{
			List<Resources> list=resourceService.getPersonalCourse(user.getUserId(),types);
			for (Resources resource : list)
			{
				if (null != resource.getImgsrc())
				{
					String imgPath = filesystem + resource.getImgsrc();
					resource.setImgsrc(imgPath);
				}
			}

			model.addAttribute("list", list);

		} catch (Exception e)
		{
			e.printStackTrace();
		
			log.error("getCollections", e);
		}
		if (types == 1)
		{
			return "/PersonalCenter/personalvideo";
		} else
		{
			return "/PersonalCenter/personalebook";
		}
	}
	
	@RequestMapping(value = "/loadmenu")
	@ResponseBody
	public String LoadMenu(HttpServletRequest request)
	{  		HttpSession sessions = request.getSession();
	       User user = (User) sessions.getAttribute(userInfo);
	        StringBuilder sb = new StringBuilder();
	if(user!=null){
		List<String> lists=user.getMenuNoLists();
		
		sb.append("<ul>");
		if (lists.indexOf("LearnNO")!=-1){
		sb.append("<li id='LastLearn' check='1'>");
		sb.append("<a href='#'><i class='fa fa-book mar5'></i>最近学习<i class='fa fa-caret-right mar20'></i></a>");
		sb.append("</li>");
	     }
		if (lists.indexOf("MyCollectionNO")!=-1){
		sb.append("<li id='MyBookmark' check='0'>");
		sb.append("<a href='#'><i class='fa fa-star mar5'></i>我的收藏<i class='fa fa-caret-right mar20'></i></a>");
		sb.append("</li>");
		}
		if (lists.indexOf("sad")!=-1){
		sb.append("<li id='MyDownload' check='0'>");
		sb.append("<a href='#'><i class='fa fa-download mar5'></i>我的下载<i class='fa fa-caret-right mar20'></i></a>");
		sb.append("</li>");
		}
		if (lists.indexOf("dd")!=-1){
		sb.append("<li id='MyCourse' check='0'>");
		sb.append("	<a class='xz' href='#'><i class='fa fa-folder mar5'></i>我的课程<i class='fa fa-caret-right mar20'></i></a>");
		sb.append("</li>");
		}
		if (lists.indexOf("zz")!=-1){
		sb.append("<li id='PersonInfor' check='0'>");
		sb.append("	<a class='xz' href='#'><i class='fa fa-address-card mar5'></i>个人资料<i class='fa fa-caret-right mar20'></i></a>");
		sb.append("</li>");
		}
		sb.append("</ul>");
		// model.addAttribute("menu", sb.toString());
	}
		return sb.toString();
	}

	@RequestMapping(value = "/resource")
	public String Resource()
	{
		return "/PersonalCenter/Resource";
	}

	@RequestMapping(value = "/personinfor")
	public String personinfor(Model model,HttpServletRequest request)
	  {   	HttpSession sessions = request.getSession();
		User userInfro = (User) sessions.getAttribute(userInfo); 
		User user=userService.getUserDetail(userInfro.getUserId(),userInfro.getUserType());
		if (user != null) {
			String img = user.getImgSrc();
			if (img != null && img != "") {
				if (user.getImgSrc().contains(filesystem) == false) {
					String imgPath = filesystem + img;
					user.setImgSrc(imgPath);
				}

			}

		}
			
	      model.addAttribute("user",user);
	      if(user.getUserType()==1){
		     return "/PersonalCenter/UserStudentInfo";
		   }else{
	  		return "/PersonalCenter/UserTeacherInfo";	  
	      }
	}
	
	@RequestMapping(value = "/collections")
	public String collections(){   
		
		return "/PersonalCenter/collection";
	}
}
