package com.singFly.cloud_examination_appUi.cloud_examination_indexController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@RequestMapping(value = "/index")
@Controller
public class IndexController {

	
	
	@RequestMapping(value = "/indexPage")
	public String indexPage() 
	{


		return "/index";
	}
	@RequestMapping(value = "/indexPage2")
	public String indexPage2() 
	{


		return "/mt2";
	}
	
	
	@RequestMapping(value = "/information")
	public String information() 
	{

		    
		return "/information";
	}
	
}
