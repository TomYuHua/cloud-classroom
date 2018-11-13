package com.singFly.cloud_examination_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;





/**
 * Hello world!
 *
 */
@SpringCloudApplication
public class CloudServiceClassroomApp {
	
	private static Logger log = LoggerFactory.getLogger(CloudServiceClassroomApp.class);
	
	public static void main(String[] args)
	{

		// SpringApplication.run(App.class, args);

		new SpringApplicationBuilder(CloudServiceClassroomApp.class).web(true).run(args);
		log.error("CloudServiceClassroomApp成功");
		System.out.println("Hello World!");
	}

}
