package com.singFly.cloud_examination_service.interfaces.Recording;

import java.util.List;

import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;

public interface RecordingBatchService {
	
	List<RecordingBatch> getRecordingBatches();
	
	List<RecordingBatch> getErrorRecordingBatches(int userId);
	
    List<RecordingBatchDetails> getDetails(int timesId);
    
    List<Question> myErrorRecordingsExcercise(int chapterId,int userId);

}
