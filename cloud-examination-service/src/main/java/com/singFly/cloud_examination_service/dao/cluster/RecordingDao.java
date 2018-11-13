package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;

import com.singFly.cloud_examination_DTO.RecordingVo;


public interface RecordingDao {
	
	boolean addRecording(List<RecordingVo> recordings);
	
	
	Integer getMaxTimesId();
	
	boolean deleteRecordingsByQuestionId(List<Integer> ids);
	
	
	boolean addQuestionRecord(List<RecordingVo> recordings);

}
