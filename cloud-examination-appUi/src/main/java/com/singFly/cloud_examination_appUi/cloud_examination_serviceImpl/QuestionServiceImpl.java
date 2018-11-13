package com.singFly.cloud_examination_appUi.cloud_examination_serviceImpl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_appUi.cloud_examination_service.QuestionService;
import com.singFly.cloud_examination_question.Question;



@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${cloud-user-service}")
	private String SERVICE_NAME;
	
	
	@HystrixCommand(fallbackMethod = "fallbackOperateQuestion")
	public boolean operateQuestion(Question question){
		return  restTemplate.postForObject("http://"+SERVICE_NAME +"/question/operateQuestion",question, boolean.class);
       
	}
	
	public boolean fallbackOperateQuestion(Question question)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}
	
	@HystrixCommand(fallbackMethod = "fallbackDeleteQuestions")
	public boolean deleteQuestions(List<Integer> ids){
		return  restTemplate.postForObject("http://"+SERVICE_NAME +"/question/deleteQuestions",ids, boolean.class);
       
	}
	
	public boolean fallbackDeleteQuestions(List<Integer> ids)
	{
		
		System.out.println("HystrixCommand fallbackMethod handle!");
		return false;
	}

	
	@HystrixCommand(fallbackMethod = "fallbackGetQuestions")
	public List<Question> getQuestions(QuestionParameters query){
		return Arrays.asList(restTemplate.postForObject("http://"+SERVICE_NAME +"/question/getQuestions",query,Question[].class));
     }
	
	public List<Question> fallbackGetQuestions(QuestionParameters query)
	{
		System.out.println("HystrixCommand fallbackMethod handle!");
		return null;
	}
}
