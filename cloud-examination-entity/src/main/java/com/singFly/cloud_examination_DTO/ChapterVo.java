package com.singFly.cloud_examination_DTO;

import com.singFly.cloud_examination_chapter.Chapter;

public class ChapterVo extends Chapter  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int totalNum;
	
	private int rightNum;

	private int wrongNum;
		
	private int unfinishedNum;
	
	private int finishedNum;
	
	private String rightRate;
	
	public int getTotalNum() {
		return totalNum;
	}

	public void setTotlNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	public String getRightRate() {
		return rightRate;
	}

	public void setRightRate(String rightRate) {
		this.rightRate = rightRate;
	}

	public int getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(int finishedNum) {
		this.finishedNum = finishedNum;
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

}
