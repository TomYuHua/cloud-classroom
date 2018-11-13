package cloud.service.classroom.dao.cluster;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import java.util.List;

public interface ChapterdirectoriesDao
{
	int insert(Chapterdirectories record);

	List<Chapterdirectories> selectAll();

	List<Chapterdirectories> SelectTwoLevelChapterInfo();

	List<Chapterdirectories> SelectChapterInfo(Integer parentId);
}