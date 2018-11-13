package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;

import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;

public interface RecordingBatchDetailsDao {
	
	
	List<RecordingBatchDetails> getDetails(int timesId);
	
	


}
