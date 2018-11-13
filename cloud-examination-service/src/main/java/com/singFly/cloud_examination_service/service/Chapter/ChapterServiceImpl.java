package com.singFly.cloud_examination_service.service.Chapter;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_chapter.Chapter;
import com.singFly.cloud_examination_service.dao.cluster.ChapterDao;
import com.singFly.cloud_examination_service.interfaces.Chapter.ChapterService;


@Service
public class ChapterServiceImpl implements ChapterService {
	
	
	@Autowired
	private ChapterDao chapterDao;

	@Override
	public boolean addChapter(Chapter chapter) {
		// TODO Auto-generated method stub
		return chapterDao.addChapter(chapter);
	}

	@Override
	public List<ChapterVo> selectChapters(int userId,int type) {
		// TODO Auto-generated method stub
		List<ChapterVo> lists=new LinkedList<>();
	if(type==0){
		lists= chapterDao.selectChaptersAboutExams(userId);
	 }else{
		lists=chapterDao.selectHeadChapters();
	    }
	    return lists;
	}

	@Override
	public boolean deleteChapters(List<Integer> ids) {
		// TODO Auto-generated method stub
		return chapterDao.deleteChapters(ids);
	}

	@Override
	public boolean updateChapter(Chapter chapter) {
		// TODO Auto-generated method stub
		return chapterDao.updateChapter(chapter);
	}

	@Override
	public Chapter getChapter(int id) {
		// TODO Auto-generated method stub
		return chapterDao.getChapter(id);
	}
	
	

}
