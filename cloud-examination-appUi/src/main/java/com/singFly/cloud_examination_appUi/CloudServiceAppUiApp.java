package com.singFly.cloud_examination_appUi;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;





@SpringCloudApplication
public class CloudServiceAppUiApp {

	private static Logger log = LoggerFactory.getLogger(CloudServiceAppUiApp.class);
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	public static void main(String[] args)
	{
		try
		{
			// mail.sendSimpleMail();
		} catch (Exception e)
		{
			log.error("Exception对象", e);
			e.printStackTrace();
		}

		log.info("信息info。。。。。。。。。。。。。。。。。");
		log.error("错误error。。。。。。。。。。。。。。。。。。。。。");
		log.debug("debug。。。。。。。。。。。。。。。。。。。。。。。");
		new SpringApplicationBuilder(CloudServiceAppUiApp.class).web(true).run(args);
		System.out.println("Hello World!");
	}
	
	/**
	 * 文件上传配置
	 * 
	 * @return
	 */
	// @Bean
	public MultipartConfigElement multipartConfigElement()
	{
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 文件最大
		factory.setMaxFileSize("5240MB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("12400MB");
		return factory.createMultipartConfig();
	}
	
}
