package com.singFly.cloud_examination_appUi.cloud_examination_service;

import java.util.List;

import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;

public interface RecordingBatchService {
	
	
	public List<RecordingBatch> getRecordingBatches();
	
	public List<RecordingBatch> getErrorRecordingBatches(int userId);
	
	public List<RecordingBatchDetails> getDetails(Integer timesId);
	
	public List<Question> myErrorRecordingsExcercise(int chapterId,int userId);

}
