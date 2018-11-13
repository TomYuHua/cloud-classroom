package com.singFly.cloud_examination_service.service.Recording;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;
import com.singFly.cloud_examination_service.dao.cluster.QuestionDao;
import com.singFly.cloud_examination_service.dao.cluster.RecordingBatchDao;
import com.singFly.cloud_examination_service.dao.cluster.RecordingBatchDetailsDao;
import com.singFly.cloud_examination_service.dao.cluster.RecordingDao;
import com.singFly.cloud_examination_service.interfaces.Recording.RecordingBatchService;

@Service
public class RecordingBatchServiceImpl implements RecordingBatchService {
	
	@Autowired
	private RecordingBatchDao  recordingBatchDao;
	
	@Autowired
	private RecordingDao recordingDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private RecordingBatchDetailsDao recordingBatchDetailsDao;
	
	public List<RecordingBatch> getRecordingBatches() {
		List<RecordingBatch> lists=new ArrayList<>();
		try{ 

				lists=recordingBatchDao.getRecordingBatches();
		
		  
	         }catch(Exception e){
		         e.printStackTrace();
		          return null;
	           }
                 return lists;
              } 
	
	
	
	public List<RecordingBatchDetails> getDetails(int timesId){
		return  recordingBatchDetailsDao.getDetails(timesId);
	}



	@Override
	public List<RecordingBatch> getErrorRecordingBatches(int userId) {
		// TODO Auto-generated method stub
		return recordingBatchDao.getErrorRecordingBatches(userId);
	}



	@Override
	public List<Question> myErrorRecordingsExcercise(int chapterId, int userId) {
		// TODO Auto-generated method stub
		return questionDao.myErrorRecordingsExcercise(chapterId, userId);
	}
        
}