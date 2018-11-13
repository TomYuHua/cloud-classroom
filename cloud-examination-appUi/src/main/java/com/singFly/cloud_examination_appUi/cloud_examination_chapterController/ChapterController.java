package com.singFly.cloud_examination_appUi.cloud_examination_chapterController;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_DTO.UserVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.ChapterService;
import com.singFly.cloud_examination_chapter.Chapter;





@RequestMapping(value = "/chapter")
@Controller
public class ChapterController {
	
private static Logger log = LoggerFactory.getLogger(ChapterController.class);
	

	
	@Autowired
	private ChapterService chapterService;
	
	
	private static final String userInfo = "userInfoId";
	
	@RequestMapping(value = "/addChapter")
	@ResponseBody
	public JSONObject addChapter(@RequestBody Chapter chapter) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=chapterService.addChapter(chapter);
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
	
	@RequestMapping(value = "/deleteChapters")
	@ResponseBody
	public JSONObject deleteChapters(@RequestBody List<Integer> ids) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			
			boolean result=chapterService.deleteChapters(ids);
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
    
	
	@RequestMapping(value = "/updateChapter")
	@ResponseBody
	public JSONObject updateChapter(@RequestBody Chapter chapter) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=chapterService.updateChapter(chapter);
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
	
	@RequestMapping(value = "/selectChapters")
	public String selectChapters(Model model,HttpServletRequest request) 
	{      
		List<ChapterVo> chapters =new LinkedList<ChapterVo>();
	
	 try {
		   
			HttpSession sessions = request.getSession();

			 UserVo user = (UserVo) sessions.getAttribute(userInfo);
		        int  type=0;
		        int   id=1;
			 chapters=chapterService.selectChapters(id,type);

		   }catch (Exception e) {
			
			log.error("操作失败 ", e);
		   }
		     model.addAttribute("chapters",chapters);
		     
		    return "/practiseInOrder";
	     }
	
	@RequestMapping(value = "/getChapter")
	@ResponseBody
	public Chapter getChapter(int id) 
	{      
		Chapter chapter =new Chapter();
	
	 try {
		  
		 chapter=chapterService.getChapter(id);

		   }catch (Exception e) {
			
			log.error("操作失败 ", e);
		}
		
		  return chapter;
	}
}
