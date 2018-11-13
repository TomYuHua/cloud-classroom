package com.singFly.cloud_examination_service.interfaces.Recording;

import java.util.List;

import com.singFly.cloud_examination_DTO.FeedBack;
import com.singFly.cloud_examination_DTO.RecordingVo;

public interface RecordingService {
	
	FeedBack addRecording(List<RecordingVo> recordings);

}
