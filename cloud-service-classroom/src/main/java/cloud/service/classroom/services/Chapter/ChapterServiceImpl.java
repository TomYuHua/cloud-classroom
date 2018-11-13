package cloud.service.classroom.services.Chapter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.entity.classroom.every.Chapter;
import cloud.service.classroom.dao.cluster.ChapterDao;
import cloud.service.classroom.dao.cluster.ChapterdirectoriesDao;
import cloud.service.classroom.interfaces.ChapterService;

@Service
public class ChapterServiceImpl implements ChapterService
{

	private Logger log = LoggerFactory.getLogger(ChapterServiceImpl.class);

	@Autowired
	private ChapterDao chapterDao;
	@Autowired
	ChapterdirectoriesDao chapterdirectoriesDao;


	@Override
	public boolean insertChapter(Chapter chapter)
	{
		try
		{
			chapterDao.insertChapter(chapter);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean deleteChapter(Integer id)
	{
		try
		{
			chapterDao.deleteChapter(id);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean updateChapter(Chapter chapter)
	{
		try
		{
			chapterDao.updateChapter(chapter);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public List<Chapter> getByPage(int page, int rows, Chapter chapter) throws Exception
	{
		Page<Chapter> chapterPage = PageHelper.startPage(page, rows, true);
		List<Chapter> chapters = chapterDao.getByPage(chapter);
		return chapters;
	}

	@Override
	public List<Chapterdirectories> SelectTwoLevelChapterInfo()
	{

		try
		{
			return chapterdirectoriesDao.SelectTwoLevelChapterInfo();
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("SelectTwoLevelChapterInfo执行出错", e);
			return null;
		}

	}

	@Override
	public List<Chapterdirectories> SelectChapterInfo(Integer parentId)
	{
		try
		{
			return chapterdirectoriesDao.SelectChapterInfo(parentId);
		} catch (Exception e)
		{
			e.printStackTrace();
			log.error("SelectChapterInfo执行出错", e);
			return null;
		}
	}

}
