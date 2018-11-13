package cloud.service.classroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.entity.classroom.Chapter.Chapterdirectories;
import cloud.service.classroom.interfaces.ChapterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/chapter")
public class ChapterController
{
	@Autowired
	private ChapterService chapterService;


	@ApiOperation(value = "目录", notes = "获取目录")
	@ApiImplicitParam(paramType = "query",name = "parentId", value = "0", required = true, dataType = "Integer",defaultValue="0")
	@RequestMapping("/getchapterinfo")
	public List<Chapterdirectories> SelectChapterInfo(Integer parentId)
	{

		return chapterService.SelectChapterInfo(parentId);
	}

	@RequestMapping("/gettwoLevelchapterinfo")
	public List<Chapterdirectories> SelectTwoLevelChapterInfo()
	{

		return chapterService.SelectTwoLevelChapterInfo();
	}
}
