package com.singFly.cloud_examination_DTO;

public class QuestionParameters {
	
	
	private int oprationType;
	
	private int number;
	
	private int userId;
	
	private int chapterId;

	public int getOprationType() {
		return oprationType;
	}

	public void setOprationType(int oprationType) {
		this.oprationType = oprationType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

}
