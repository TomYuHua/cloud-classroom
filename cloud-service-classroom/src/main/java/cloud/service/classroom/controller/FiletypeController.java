package cloud.service.classroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cloud.entity.classroom.every.Document;
import cloud.entity.classroom.every.Filetype;
import cloud.entity.classroom.every.User;
import cloud.service.classroom.interfaces.FiletypeService;
import cloud.service.classroom.interfaces.StudentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
public class FiletypeController {
    @Autowired
	private FiletypeService filetypeService;
	
	
	@ApiOperation(value = "保存文件类型信息", notes = "保存文件类型的单个信息")
	@ApiImplicitParam(name = "filetype", value = "文件的实体类", required = true, dataType = "Filetype")
	@RequestMapping(value = "/filetype/filetype", method = RequestMethod.POST)
	public String saveFiletype(@RequestBody Filetype filetype)
	{
		if(filetypeService.insert(filetype)){
	    	return "success";
	   }else{
	    	return "fail";
	    }
	}
	
	
	@ApiOperation(value = "展示类型个人信息", notes = "通过id展示文件类型的单个信息")
	@ApiImplicitParam(name = "id", value = "文件类型的id", required = true, dataType = "Integer")
	@RequestMapping(value = "/filetype/{id}", method = RequestMethod.GET)
	public Filetype getStudent(@PathVariable Integer id) throws Exception
	{
		Filetype filetype = filetypeService.getFileType(id);
		 return filetype;

	}
	
	@ApiOperation(value = "展示文件类型分页", notes = "通过查询条件来控制文件类型显示")
	@ApiImplicitParam(name = "filetype", value = "文档的查询条件", required = true, dataType = "Filetype")
	@RequestMapping(value = "/filetype/filetype", method = RequestMethod.GET)
	public List<Filetype> getPage(@RequestParam int page, @RequestParam int rows, @RequestBody Filetype filetype)
			throws Exception
	{
		List<Filetype> filetypes = filetypeService.getByPage(page, rows,  filetype);
		return filetypes;

	}
	
	
//	@ApiOperation(value = "更新文件类型信息", notes = "通过实体类,更新文档信息")
//	@ApiImplicitParam(name = "document", value = "文档实体类", required = true, dataType = "Document")
//	@RequestMapping(value = "/document/document", method = RequestMethod.PUT)
//	public String updateDocument(@RequestBody Filetype filetype)
//	{
//
//		if (filetypeService.update(filetype))
//		{
//			return "success";
//		} else
//		{
//			return "fail";
//		}
//	}
	
	@ApiOperation(value = "删除文件类型信息", notes = "通过id,删除文件类型")
	@ApiImplicitParam(name = "id", value = "文件类型id号", required = true, dataType = "Integer")
	@RequestMapping(value = "/filetype/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Integer id)
	{

		if (filetypeService.delete(id))
		{
			return "success";
		} else
		{
			return "fail";
		}
	}
	
}
