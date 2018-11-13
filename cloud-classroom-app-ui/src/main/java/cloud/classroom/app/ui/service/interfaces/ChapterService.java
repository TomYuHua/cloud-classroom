package cloud.classroom.app.ui.service.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import cloud.entity.classroom.Chapter.Chapterdirectories;

public interface ChapterService
{
	List<Chapterdirectories> SelectTwoLevelChapterInfo();

	List<Chapterdirectories> SelectChapterInfo(Integer parentId);
}
