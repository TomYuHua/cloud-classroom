package com.singFly.cloud_examination_service.interfaces.Chapter;

import java.util.List;

import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_chapter.Chapter;

public interface ChapterService {
	
	boolean addChapter(Chapter chapter);
	
	List<ChapterVo> selectChapters(int userId,int id);
	
	boolean deleteChapters(List<Integer> ids);
	
	
	boolean updateChapter(Chapter chapter);
	
	Chapter getChapter(int id);

}
