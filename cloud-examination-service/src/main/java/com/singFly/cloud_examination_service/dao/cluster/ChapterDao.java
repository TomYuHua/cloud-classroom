package com.singFly.cloud_examination_service.dao.cluster;

import java.util.List;
import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_chapter.Chapter;

public interface ChapterDao {
	
	boolean addChapter(Chapter chapter);
	
	List<ChapterVo> selectHeadChapters();
	
	List<ChapterVo> selectChaptersAboutExams(int userId);
	
	boolean deleteChapters(List<Integer> ids);
	
	boolean updateChapter(Chapter chapter);

    Chapter getChapter(int id);
    
    

}
