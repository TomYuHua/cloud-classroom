package com.singFly.cloud_examination_appUi.cloud_examination_questionController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.jackson.builder.StringInterningAmazonInfoBuilder;
import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_DTO.RecordingVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.QuestionService;
import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recording.Recording;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBean.RecordingBean;
import com.singFly.cloud_examination_user.User;





@RequestMapping(value = "/question")
@Controller
public class QuestionController {
	
	private static Logger log = LoggerFactory.getLogger(QuestionController.class);
	

	private static final String userInfo = "userInfoId";
	
	@Autowired
	private QuestionService questionService;
	

	
	@RequestMapping(value = "/operateQuestion")
	@ResponseBody
	public JSONObject operateQuestion(@RequestBody Question question) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=questionService.operateQuestion(question);
			if (result)
			{
				jsonObj.put("result", "success");
			} else
			{
				jsonObj.put("result", "fail");
			}
		   }catch(Exception e)
		   {

			    log.error("操作失败 ", e);
	 	   }

		return jsonObj;
	}
	
	@RequestMapping(value = "/deleteQuestions")
	@ResponseBody
	public JSONObject deleteQuestions(@RequestBody List<Integer> ids) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			
			boolean result=questionService.deleteQuestions(ids);
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

	
	
	@RequestMapping(value = "/getQuestions")
	public String getQuestions(String number,String operationType,String id,Model model) 
	{
		List<Question> list=new LinkedList<Question>();
		RecordingBean recordingBean=new RecordingBean();
		List<RecordingVo> recordingList=new ArrayList<>();  
		try
		{    
		//	HttpSession sessions = request.getSession();
		//	User user = (User) sessions.getAttribute(userInfo);
			//query.setUserId(user.getId());
			id="1";
			QuestionParameters query=new QuestionParameters();
			query.setOprationType(Integer.valueOf(operationType));
			query.setNumber(Integer.valueOf(number));
			query.setUserId(1);
			query.setChapterId(Integer.valueOf(id));
			list=questionService.getQuestions(query);	
			for(Question question:list){
				RecordingVo recording=new RecordingVo();
				recording.setQuestionId(question.getId());
				recording.setUserId(1);
				recording.setRightAnswer(question.getResults());
				recording.setChapterId(question.getChapterId());
				recordingList.add(recording);
			}
			recordingBean.setRecordingList(recordingList);
		    model.addAttribute("list",list);
			model.addAttribute("recordingBean",recordingBean);
		} catch (Exception e)
		{

			log.error("操作失败 ", e);
		}

		  return "/order-zt";
	}

	@RequestMapping(value = "/chapterDetails/{totalNum}/{finishedNum}/{rightNum}/{unfinishedNum}/{wrongNum}/{id}")
	public String chapteraDatails(@PathVariable("totalNum")String totalNum,@PathVariable("finishedNum")String finishedNum,@PathVariable("rightNum")String rightNum,@PathVariable("unfinishedNum")String unfinishedNum,@PathVariable("wrongNum")String wrongNum,@PathVariable("id")String id,Model model){
	     
	     model.addAttribute("totalNum",totalNum);
	     model.addAttribute("finishedNum",finishedNum);
	     model.addAttribute("rightNum",rightNum);
	     model.addAttribute("unfinishedNum",unfinishedNum);
	     model.addAttribute("wrongNum",wrongNum);
	     model.addAttribute("id",id);
		
	     return "/practiseDetail";
	}
    
	@RequestMapping(value = "/loadPage")
	public String loadPage(String number,String operationType,String id,Model model){
	      model.addAttribute("number",number);
	      model.addAttribute("operationType",operationType);
	      model.addAttribute("id",id);
		  model.addAttribute("type","0");
		return "/index2";
	}
	

	
	
}
