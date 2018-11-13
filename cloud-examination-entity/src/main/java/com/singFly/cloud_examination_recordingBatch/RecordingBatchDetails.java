package com.singFly.cloud_examination_recordingBatch;

import org.hamcrest.core.Is;

import com.singFly.cloud_examination_question.Question;

public class RecordingBatchDetails extends Question {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int isCorrect;
    
	private int timesId;
	
	private String inputAnswer;

	public int getTimesId() {
		return timesId;
	}


	public void setTimesId(int timesId) {
		this.timesId = timesId;
	}


	public int getIsCorrect() {
		return isCorrect;
	}


	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public String getInputAnswer() {
		return inputAnswer;
	}


	public void setInputAnswer(String inputAnswer) {
		this.inputAnswer = inputAnswer;
	}
	
	

}
