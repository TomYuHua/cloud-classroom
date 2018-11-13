package com.singFly.cloud_examination_recording;

import java.io.Serializable;
import java.security.Timestamp;

public class Recording implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	public int getTimesId() {
		return timesId;
	}

	public void setTimesId(int timesId) {
		this.timesId = timesId;
	}

	private int questionId;
	
	private int  chapterId;
	
	
	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	private String inputAnswer;
	
	private int isCorrect;
	
	private int userId;
	
	private int timesId;
	
	private  Timestamp inputTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getInputAnswer() {
		return inputAnswer;
	}

	public void setInputAnswer(String inputAnswer) {
		this.inputAnswer = inputAnswer;
	}

	public int getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getInputTime() {
		return inputTime;
	}

	public void setInputTime(Timestamp inputTime) {
		this.inputTime = inputTime;
	}




}
