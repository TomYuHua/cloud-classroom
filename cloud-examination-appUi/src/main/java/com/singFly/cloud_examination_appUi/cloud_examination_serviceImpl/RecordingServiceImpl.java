package com.singFly.cloud_examination_appUi.cloud_examination_serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singFly.cloud_examination_DTO.FeedBack;
import com.singFly.cloud_examination_DTO.RecordingVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.RecordingService;
import com.singFly.cloud_examination_recording.Recording;


@Service
public class RecordingServiceImpl implements RecordingService  {

	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${cloud-user-service}")
	private String SERVICE_NAME;

	
	@HystrixCommand(fallbackMethod = "fallbackAddRecording")
	public FeedBack addRecording(List<RecordingVo> recordings) {
		
		return restTemplate.postForObject("http://"+SERVICE_NAME +"/recording/addRecordings",recordings, FeedBack.class);
	}

	public FeedBack fallbackAddRecording(List<RecordingVo> recordings)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}



}
