package cloud.service.classroom.interfaces;

import java.util.List;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.every.Chapter;

public interface ChapterService
{

	public boolean insertChapter(Chapter chapter);

	public boolean deleteChapter(Integer id);

	public boolean updateChapter(Chapter chapter);

	public List<Chapter> getByPage(int page, int rows, Chapter chapter) throws Exception;

	List<Chapterdirectories> SelectTwoLevelChapterInfo();

	List<Chapterdirectories> SelectChapterInfo(Integer parentId);
}
