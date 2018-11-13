package com.singFly.cloud_examination_DTO;

import com.singFly.cloud_examination_question.Question;

public class QuestionVo extends Question {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int operationType;


	public int getOperationType() {
		return operationType;
	}


	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	

}
