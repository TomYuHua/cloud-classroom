package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_DTO.QuestionVo;
import com.singFly.cloud_examination_question.Question;


public interface QuestionDao {


	boolean addQuestion(QuestionVo question);
	
	boolean updateQuestion(QuestionVo question);
	
	boolean deleteQuestions(List<Integer> ids);
	
	List<QuestionVo> selectByRandom();
	
	List<QuestionVo> selectUnFiQu(QuestionParameters query);
	
	List<QuestionVo> selectFiQu(QuestionParameters query);
	
	List<QuestionVo> selectErQu(QuestionParameters query);
	
	List<QuestionVo> selectByUserError(Integer userId);
	
	List<Question>  myErrorRecordingsExcercise(@Param("chapterId")int chapterId,@Param("userId")int userId);

}
