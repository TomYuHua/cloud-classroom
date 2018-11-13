package cloud.service.classroom.dao.cluster;

import java.util.List;

import cloud.entity.classroom.every.Chapter;

public interface ChapterDao {
	
	int insertChapter(Chapter chapter);

	int deleteChapter(Integer id);
    
	int updateChapter(Chapter chapter);
	
	List<Chapter> getByPage(Chapter chapter);
}
