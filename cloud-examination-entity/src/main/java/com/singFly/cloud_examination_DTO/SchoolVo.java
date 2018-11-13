package com.singFly.cloud_examination_DTO;

import com.singFly.cloud_examination_school.School;

public class SchoolVo extends School {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getOprationType() {
		return oprationType;
	}

	public void setOprationType(int oprationType) {
		this.oprationType = oprationType;
	}

	private int oprationType;

}
