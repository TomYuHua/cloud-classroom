package com.singFly.cloud_examination_chapter;

import java.io.Serializable;

public class Chapter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private  String chapterName;
    
    private  int parentId;
    
    

	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}



}
