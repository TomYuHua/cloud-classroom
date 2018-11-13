package com.singFly.cloud_examination_appUi.cloud_examination_clazzController;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.singFly.cloud_examination_DTO.ClazzVo;
import com.singFly.cloud_examination_appUi.cloud_examination_service.ClazzService;
import com.singFly.cloud_examination_clazz.Clazz;
import com.singFly.cloud_examination_idsBean.IdsBean;
import com.singFly.cloud_examination_recordingBean.RecordingBean;


@RequestMapping("/clazz")
@Controller
public class ClazzController {
	
	private static Logger log = LoggerFactory.getLogger(ClazzController.class);
	
	@Autowired
	private ClazzService clazzService;
	
	@RequestMapping("/getClazzLists")
	public  String getClazzLists(Model model){
		Integer schoolId=null;
		String clazzName=null;
		List<ClazzVo> clazzLists=clazzService.getClazzLists(schoolId, clazzName);
		model.addAttribute("clazzLists", clazzLists);
		return "/class";
	}
	
	
	@RequestMapping(value = "/addClazz")
	@ResponseBody
	public JSONObject addClazz(String clazzName,int schoolId) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=clazzService.addClazz(clazzName,schoolId);
			if (result)
			{
				jsonObj.put("result", "success");
			} else
			{
				jsonObj.put("result", "fail");
			}
		} catch (Exception e)
		{

			log.error("操作失败 ", e);
		}

		return jsonObj;
	}
	
	@RequestMapping(value = "/updateClazz")
	@ResponseBody
	public JSONObject updateClazz(String clazzName,String schoolId,String id) 
	{   Clazz clazz=new Clazz();
	     clazz.setClazzName(clazzName);
	     clazz.setSchoolId(Integer.valueOf(schoolId));
	     clazz.setId(Integer.valueOf(id));
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=clazzService.updateClazz(clazz);
			if (result)
			{
				jsonObj.put("result", "success");
			} else
			{
				jsonObj.put("result", "fail");
			}
		} catch (Exception e)
		{
            e.printStackTrace();
			log.error("操作失败 ", e);
		}

		return jsonObj;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/deleteClazz")
	@ResponseBody
	public JSONObject deleteClazz(@ModelAttribute(value="idsBean")IdsBean idsBean) 
	{
		JSONObject jsonObj = new JSONObject();
		try
		{
			boolean result=clazzService.deleteClazz(idsBean);
			if (result)
			{
				jsonObj.put("result", "success");
			} else
			{
				jsonObj.put("result", "fail");
			}
		} catch (Exception e)
		{

			log.error("操作失败 ", e);
		}

		return jsonObj;
	}

     @RequestMapping("/getClazzById/{id}")  
      public String getClazzById(@PathVariable("id")String id,Model model) {
		ClazzVo clazz=clazzService.getClazzById(Integer.valueOf(id));
		model.addAttribute("clazz",clazz);
		return "/editClass";
	}
     
     
     @RequestMapping("/deleteDetails/{id}/{clazzName}")  
     public String deleteDetails(@PathVariable("id")String id,@PathVariable("clazzName")String clazzName,Model model) {
	      IdsBean idsBean=new IdsBean();
          List<Integer> ids=new ArrayList<>();
	      ids.add(Integer.valueOf(id));
	      idsBean.setIds(ids);
    	 model.addAttribute("idsBean",idsBean);  
 	    model.addAttribute("clazzName",clazzName);
		return "/deleteClass";
	}
	
}
