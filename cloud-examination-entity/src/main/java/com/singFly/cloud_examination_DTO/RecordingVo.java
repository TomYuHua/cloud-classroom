package com.singFly.cloud_examination_DTO;

import com.singFly.cloud_examination_recording.Recording;

public class RecordingVo extends Recording {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rightAnswer;
	
	public String getRightAnswer() {
		return rightAnswer;
	}


	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}


}
