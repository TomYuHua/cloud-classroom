package com.singFly.cloud_examination_service.interfaces.Question;


import java.util.List;

import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_DTO.QuestionVo;


public interface QuestionService {
	
	public boolean operateQuestion(QuestionVo question);
	
	public boolean deleteQuestions(List<Integer> ids);
	
	public List<QuestionVo> getQuestions(QuestionParameters query);

}
