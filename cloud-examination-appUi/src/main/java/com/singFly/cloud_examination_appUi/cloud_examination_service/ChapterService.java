package com.singFly.cloud_examination_appUi.cloud_examination_service;

import java.util.List;

import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_chapter.Chapter;

public interface ChapterService {
	
	public boolean addChapter(Chapter chapter);
	
	public boolean updateChapter(Chapter chapter);
	
	public boolean deleteChapters(List<Integer> ids);
	
	public List<ChapterVo> selectChapters(int id,int type);
	
	public Chapter getChapter(int id);
	

}
