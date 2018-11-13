package com.singFly.cloud_examination_appUi.cloud_examination_serviceImpl;

import static org.mockito.Matchers.isNotNull;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singFly.cloud_examination_appUi.cloud_examination_service.RecordingBatchService;
import com.singFly.cloud_examination_question.Question;
import com.singFly.cloud_examination_recording.Recording;
import com.singFly.cloud_examination_recordingBatch.RecordingBatch;
import com.singFly.cloud_examination_recordingBatch.RecordingBatchDetails;
import com.singFly.cloud_examination_user.User;


@Service
public class RecordingBatchServiceImpl implements RecordingBatchService {
	
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	
	@HystrixCommand(fallbackMethod = "fallbackGetRecordingBatches")
	public List<RecordingBatch> getRecordingBatches() {
		
		return Arrays.asList(restTemplate.getForObject("http://"+SERVICE_NAME +"/recordingBatch/getRecordingBatches",RecordingBatch[].class));
	}

	public List<RecordingBatch> fallbackGetRecordingBatches()
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}
	
	public List<RecordingBatchDetails> getDetails(Integer timesId){
		return Arrays.asList(restTemplate.postForObject("http://"+SERVICE_NAME+"/recordingBatch/getDetails",timesId,RecordingBatchDetails[].class));
	}
	
	public List<RecordingBatchDetails> fallbackGetDetails(Integer timesId){
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@Override
	public List<RecordingBatch> getErrorRecordingBatches(int userId) {
		// TODO Auto-generated method stub
		return Arrays.asList(restTemplate.postForObject("http://"+SERVICE_NAME +"/recordingBatch/getErrorRecordingBatches",userId,RecordingBatch[].class));
	}
	
	
	@HystrixCommand(fallbackMethod = "fallbackGetErrorRecordingBatches")
	public List<RecordingBatch> fallbackGetErrorRecordingBatches(int userId)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

	@Override
	public List<Question> myErrorRecordingsExcercise(int chapterId, int userId) {
		// TODO Auto-generated method stub
		return Arrays.asList(restTemplate.getForObject("http://"+SERVICE_NAME +"/recordingBatch/myErrorRecordingsExcercise?chapterId={chapterId}&userId={userId}",Question[].class,chapterId,userId));
	}
	
	@HystrixCommand(fallbackMethod = "fallbackMyErrorRecordingsExcercise")
	public List<Question> fallbackMyErrorRecordingsExcercise(int chapterId, int userId)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}

}
