package com.singFly.cloud_examination_commonDfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellowController
{
	@ResponseBody
	@RequestMapping("/hellorest")
	public String HelloRest()    
	{
		 
		  return "你是煮";
	}
}
