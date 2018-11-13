package com.singFly.cloud_examination_appUi.cloud_examination_service;

import java.util.List;

import com.singFly.cloud_examination_DTO.FeedBack;
import com.singFly.cloud_examination_DTO.RecordingVo;


public interface RecordingService {
	
	public FeedBack addRecording(List<RecordingVo> recordings);

}
