package com.singFly.cloud_examination_recordingBatch;

import java.io.Serializable;
import java.sql.Timestamp;

public class RecordingBatch implements Serializable  {
	
private static final long serialVersionUID = 1L;
	
	private String chapterName;
	
	private Timestamp practiseTime;
	
	private String totalTime;
	
	private int rightNum;

	private int wrongNum;
	
	private int unfinishedNum;
	
	private int  totalNum;
	
	private int  score;
	
	private int  chapterId;
	
	
	
	public int getTimesId() {
		return timesId;
	}

	public void setTimesId(int timesId) {
		this.timesId = timesId;
	}

	private int  timesId;

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Timestamp getPractiseTime() {
		return practiseTime;
	}

	public void setPractiseTime(Timestamp practiseTime) {
		this.practiseTime = practiseTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
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

	public int getUnfinishedNum() {
		return unfinishedNum;
	}

	public void setUnfinishedNum(int unfinishedNum) {
		this.unfinishedNum = unfinishedNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
}
