package com.singFly.cloud_examination_service.service.Recording;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singFly.cloud_examination_DTO.FeedBack;
import com.singFly.cloud_examination_DTO.RecordingVo;
import com.singFly.cloud_examination_recording.Recording;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_service.dao.cluster.FeedBackDao;
import com.singFly.cloud_examination_service.dao.cluster.RecordingDao;
import com.singFly.cloud_examination_service.interfaces.Recording.RecordingService;




@Service
public class RecordingServiceImpl implements RecordingService {

	@Autowired
	private RecordingDao  recordingDao;
	
	
	@Autowired
	private FeedBackDao  feedBackDao;
	
	@Override
	public FeedBack addRecording(List<RecordingVo> recordings) {
		FeedBack feedBack=new FeedBack();
		boolean result=false;
	 try{
	     synchronized(this){
		 Integer maxTimesId=recordingDao.getMaxTimesId();
		 if(maxTimesId==null){
			 maxTimesId=1;
		     }else{
		 maxTimesId=maxTimesId+1;}
		List<Integer> ids=new ArrayList<>();
	    for(Recording recording:recordings){
			recording.setTimesId(maxTimesId);
			ids.add(recording.getQuestionId());
		 }    
		 recordingDao.deleteRecordingsByQuestionId(ids);	 
			
	     result = recordingDao.addRecording(recordings);
	        if(result){
	       feedBack= feedBackDao.getStaticFeedBack(maxTimesId);  
	          feedBack.setTimesId(maxTimesId);
	        }
	               
	 }
			recordingDao.addQuestionRecord(recordings); 
		         }catch(Exception e){
		             e.printStackTrace();
			        return null;
		        }
		         return feedBack;
	         }
	
	

}
