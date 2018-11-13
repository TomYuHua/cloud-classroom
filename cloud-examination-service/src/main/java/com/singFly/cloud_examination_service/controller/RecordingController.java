package com.singFly.cloud_examination_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.singFly.cloud_examination_DTO.FeedBack;
import com.singFly.cloud_examination_DTO.RecordingVo;
import com.singFly.cloud_examination_recording.Recording;
import com.singFly.cloud_examination_service.interfaces.Recording.RecordingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController       
@RequestMapping(value = "/recording")
public class RecordingController {
	
	@Autowired
	private RecordingService recordingService; 
	
	
	@ApiOperation(value = "批量添加考试记录", notes = "批量添加考试记录")
	@ApiImplicitParam(name = "recordings", dataType = "List", required = true, value = "记录的集合")
	@RequestMapping(value = "/addRecordings", method = RequestMethod.POST)
	public FeedBack addRecordings(@RequestBody List<RecordingVo> recordings){
      return recordingService.addRecording(recordings);
	}
}
