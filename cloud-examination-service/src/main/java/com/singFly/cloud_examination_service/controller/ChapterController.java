package com.singFly.cloud_examination_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.singFly.cloud_examination_DTO.ChapterVo;
import com.singFly.cloud_examination_chapter.Chapter;
import com.singFly.cloud_examination_service.interfaces.Chapter.ChapterService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

 
@RequestMapping(value = "/chapter")
@RestController  
public class ChapterController {
	

	@Autowired
	private ChapterService chapterService;
	
	@ApiOperation(value = "添加章节", notes = "添加章节")
	@ApiImplicitParam(name = "chapter", dataType = "Chapter", required = true, value = "章节实体类")
	@RequestMapping(value = "/addChapter", method = RequestMethod.POST)
	public boolean addChapter(@RequestBody Chapter chapter){
	
		return chapterService.addChapter(chapter);
    }
	
	
	@ApiOperation(value = "删除章节", notes = "删除章节")
	@ApiImplicitParam(name = "ids", dataType = "List", required = true, value = "章节的一些id号")
	@RequestMapping(value = "/deleteChapters", method = RequestMethod.POST)
	public boolean deleteChapters(@RequestBody List<Integer> ids){
	
		return chapterService.deleteChapters(ids);
    }
	
	
	@ApiOperation(value = "添加章节", notes = "添加章节")
	@ApiImplicitParam(name = "chapter", dataType = "Chapter", required = true, value = "章节实体类")
	@RequestMapping(value = "/updateChapter", method = RequestMethod.POST)
	public boolean updateChapter(@RequestBody Chapter chapter){
	
		return chapterService.updateChapter(chapter);
    }
	
	@ApiOperation(value = "搜索考试章节", notes = "搜索考试章节")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "用户的id"),
		@ApiImplicitParam(paramType = "query", name = "type", dataType = "int", required = true, value = "操作的类型")})
	@RequestMapping(value = "/selectChapters", method = RequestMethod.GET)
	public List<ChapterVo> selectChapters(int id,int type){

		return chapterService.selectChapters(id,type);
    }
	
	@ApiOperation(value = "获取章节详情", notes = "获取章节详情")
	@ApiImplicitParam(name = "id", dataType = "int", required = true, value = "章节id")
	@RequestMapping(value = "/getChapter", method = RequestMethod.GET)
	public Chapter getChapter(int id){
	
		return chapterService.getChapter(id);
    }
	

}
