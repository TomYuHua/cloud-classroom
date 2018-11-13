package com.singFly.cloud_examination_appUi.cloud_examination_service;

import java.util.List;

import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_question.Question;

public interface QuestionService {
	
	public boolean  operateQuestion(Question question);
	
	public boolean deleteQuestions(List<Integer> ids);
	 
	public List<Question> getQuestions(QuestionParameters query);
	

}
