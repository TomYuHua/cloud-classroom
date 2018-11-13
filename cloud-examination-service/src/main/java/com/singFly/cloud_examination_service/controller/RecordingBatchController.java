package com.singFly.cloud_examination_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.singFly.cloud_examination_chapter.Chapter;
import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;
import com.singFly.cloud_examination_service.interfaces.Question.QuestionService;
import com.singFly.cloud_examination_service.interfaces.Recording.RecordingBatchService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/recordingBatch")
@RestController  
public class RecordingBatchController {

	@Autowired
	private RecordingBatchService recordingBatchService;
	

	@RequestMapping(value = "/getRecordingBatches",method=RequestMethod.GET)
	public List<RecordingBatch> getRecordingBatches(){
	
		return recordingBatchService.getRecordingBatches();
    }
	
	
	@RequestMapping(value = "/getDetails",method=RequestMethod.POST)
	public List<RecordingBatchDetails> getDetails(@RequestBody Integer timesId){
        return recordingBatchService.getDetails(timesId);
	}
	
	@RequestMapping(value = "/getErrorRecordingBatches",method=RequestMethod.POST)
	public List<RecordingBatch> getErrorRecordingBatches(@RequestBody int userId){
	
		return recordingBatchService.getErrorRecordingBatches(userId);
    }
	
	@RequestMapping(value = "/myErrorRecordingsExcercise",method=RequestMethod.GET)
	public List<Question> myErrorRecordingsExcercise(int chapterId,int userId){
	
		return recordingBatchService.myErrorRecordingsExcercise(chapterId,userId);
    }
}
