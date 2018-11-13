package com.singFly.cloud_examination_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.singFly.cloud_examination_DTO.QuestionParameters;
import com.singFly.cloud_examination_DTO.QuestionVo;
import com.singFly.cloud_examination_service.interfaces.Question.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


@RestController       
@RequestMapping(value = "/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@ApiOperation(value = "添加或修改试题", notes = "添加或修改试题")
	@ApiImplicitParam(name = "question", dataType = "Question", required = true, value = "用户实体类")
	@RequestMapping(value = "/operateUser", method = RequestMethod.POST)
	public boolean operateQuestion(@RequestBody QuestionVo question) throws Exception{
	
		return questionService.operateQuestion(question);
    }
	
	
	@ApiOperation(value = "删除试题", notes = "删除试题")
	@ApiImplicitParam(name = "ids", dataType = "List", required = true, value = "试题的一些id号")
	@RequestMapping(value = "/deleteQuestions", method = RequestMethod.POST)
	public boolean deleteQuestion(@RequestBody List<Integer> ids){
	
		return questionService.deleteQuestions(ids);
    }
	

	
	@ApiOperation(value = "获取试题", notes = "获取试题")
	@ApiImplicitParam(name = "query", dataType = "QuestionParameters", required = true, value = "查询的一些参数")
	@RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
	public List<QuestionVo> getQuestions(@RequestBody QuestionParameters query){
	
		return questionService.getQuestions(query);
    }

}
