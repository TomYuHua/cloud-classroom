package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;

import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;

public interface RecordingBatchDao {
	
	List<RecordingBatch> getRecordingBatches();
	
	List<RecordingBatch> getErrorRecordingBatches(int userId);
	
	

}
