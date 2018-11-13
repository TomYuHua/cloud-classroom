package com.singFly.cloud_examination_DTO;

public class FeedBack {
	
	private int scores;
	
	public int getTimesId() {
		return timesId;
	}

	public void setTimesId(int timesId) {
		this.timesId = timesId;
	}

	private int totalNum;
	
	private int timesId;
	
	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	public int getWrongNum() {
		return wrongNum;
	}

	public void setWrongNum(int wrongNum) {
		this.wrongNum = wrongNum;
	}

	public int getUnFinishNum() {
		return unFinishNum;
	}

	public void setUnFinishNum(int unFinishNum) {
		this.unFinishNum = unFinishNum;
	}

	private int rightNum;
	
	private int wrongNum;
	
	private int unFinishNum;

}
