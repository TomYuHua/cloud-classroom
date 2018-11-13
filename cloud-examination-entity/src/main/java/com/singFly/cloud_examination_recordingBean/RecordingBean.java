package com.singFly.cloud_examination_recordingBean;

import java.util.List;

import com.singFly.cloud_examination_DTO.RecordingVo;
import com.singFly.cloud_examination_question.Question;


public class RecordingBean {
	
	public List<RecordingVo>  recordingList;
	
	public List<Question>  list;

	public List<RecordingVo> getRecordingList() {
		return recordingList;
	}

	public void setRecordingList(List<RecordingVo> recordingList) {
		this.recordingList = recordingList;
	}
	
	public List<Question> getList() {
		return list;
	}

	public void setList(List<Question> list) {
		this.list = list;
	}
	

}
