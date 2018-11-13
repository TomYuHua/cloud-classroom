package com.singFly.cloud_examination_appUi.cloud_examination_recordingController;


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.singFly.cloud_examination_DTO.FeedBack;
import com.singFly.cloud_examination_DTO.RecordingVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.RecordingBatchService;
import com.singFly.cloud_examination_appUi.cloud_examination_service.RecordingService;
import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;
import com.singFly.cloud_examination_recordingBean.RecordingBean;



@Controller
@RequestMapping(value = "/recording")
public class RecordingController {
	
	private static Logger log = LoggerFactory.getLogger(RecordingController.class);

	@Autowired
	private RecordingService recordingService;
	
	@Autowired
	private RecordingBatchService recordingBatchService;
	
	
	@RequestMapping(value = "/addRecording")
	public String addRecording(@ModelAttribute(value="recordingBean")RecordingBean recordingBean,Model model)throws Exception
	{
		FeedBack feedBack = new FeedBack();
		try
		  { 
			feedBack=recordingService.addRecording(recordingBean.getRecordingList());
	        model.addAttribute("feedBack",feedBack);
	        model.addAttribute("type","1");
		  }catch (Exception e)
			{

				log.error("操作失败 ", e);
			}
		  return "/index2";
	}
	@RequestMapping(value = "/feedBackPage")
	public String feedBack(Model model,int totalNum,int rightNum,int wrongNum,int unFinishedNum,int scores,int timesId) throws Exception
	{
           model.addAttribute("totalNum",totalNum);
           model.addAttribute("rightNum",rightNum);
           model.addAttribute("wrongNum",wrongNum);
           model.addAttribute("unFinishedNum",unFinishedNum);
           model.addAttribute("scores",scores);
           model.addAttribute("timesId",timesId);
		  return "/order-ok";
	}

	@RequestMapping(value = "/chapterRecordings")
	public String chapterRecordings(){
		return "/chapterRecordings";
	}
	

	@RequestMapping(value = "/myErrorRecordings")
	public String myErrorRecordings(Model model){
		int userId=1;
	  List<RecordingBatch> lists=recordingBatchService.getErrorRecordingBatches(userId);
	  model.addAttribute("lists",lists);
	  model.addAttribute("userId",userId);
		return "/myErrorRecordings";
	}
	
	@RequestMapping(value="/myErrorRecordingsExcercise")
	public String myErrorRecordingsExcercise(int chapterId,int userId,Model model){
		RecordingBean recordingBean=new RecordingBean();
		List<RecordingVo> recordingList=new ArrayList<>(); 
	    List<Question> lists=recordingBatchService.myErrorRecordingsExcercise(chapterId, userId);
	  for(Question question:lists){
			RecordingVo recording=new RecordingVo();
			recording.setQuestionId(question.getId());
			recording.setUserId(userId);
			recording.setRightAnswer(question.getResults());
			recording.setChapterId(question.getChapterId());
			recordingList.add(recording);
		   }
		  recordingBean.setRecordingList(recordingList);
	      model.addAttribute("list",lists);
		  model.addAttribute("recordingBean",recordingBean);
		 return "/order-zt";
	}
	
	
	@RequestMapping(value = "/myHiding")
	public String myHiding(){
		return "myHiding";
	}
	

	
	@RequestMapping(value = "/getRecordingBatches")
	public String getRecordingBatches(Model model){
		List<RecordingBatch> lists=recordingBatchService.getRecordingBatches();
		  model.addAttribute("lists",lists);
		return "/practice";
	}
	
	
	@RequestMapping(value = "/getDetails")
	public String getDetails(int timesId,Model model){
		List<RecordingBatchDetails> lists=recordingBatchService.getDetails(timesId);
		   
	 		RecordingBean recordingBean=new RecordingBean();
			List<RecordingVo> recordingList=new ArrayList<>(); 
			for(Question question:lists){
				RecordingVo recording=new RecordingVo();
				recording.setQuestionId(question.getId());
				recording.setUserId(1);
				recording.setRightAnswer(question.getResults());
				recording.setChapterId(question.getChapterId());
				recordingList.add(recording);
			}
			recordingBean.setRecordingList(recordingList);
		    model.addAttribute("list",lists);
			model.addAttribute("recordingBean",recordingBean);		
	       return "/order-zt";	 
	     

	     
	    }
	@RequestMapping(value = "/practiceSee")
	public String practiceSee(int timesId,int rightNum,int wrongNum,int unFinishedNum,int scores,Model model){
		List<RecordingBatchDetails> lists=recordingBatchService.getDetails(timesId); 
   	    model.addAttribute("lists",lists);
   	    model.addAttribute("timesId",timesId);
   	    model.addAttribute("rightNum",rightNum);
   	    model.addAttribute("wrongNum",wrongNum);
   	    model.addAttribute("unFinishedNum",unFinishedNum);
   	    model.addAttribute("scores",scores);    
	     return "/practice-see";
	
	}
}