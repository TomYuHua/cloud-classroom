package org.common.dfs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class CommonDfsApp
{
	private static Logger log = LoggerFactory.getLogger(CommonDfsApp.class);

	public static void main(String[] args)
	{
		try
		{
			// mail.sendSimpleMail();
		} catch (Exception e)
		{
			// log.error("Exception对象", e);
			e.printStackTrace();
		}
		// log.debug("debug。。。。。。。。。。。。。。。。。。。。。。。");
		new SpringApplicationBuilder(CommonDfsApp.class).web(true).run(args);
		System.out.println("Hello World!");
	}
}
