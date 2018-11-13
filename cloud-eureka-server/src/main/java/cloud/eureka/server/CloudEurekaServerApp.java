package cloud.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */

@EnableEurekaServer
@SpringBootApplication
public class CloudEurekaServerApp
{
	public static void main(String[] args)
	{
		new SpringApplicationBuilder(CloudEurekaServerApp.class).web(true).run(args);
		System.out.println("Hello World!");
	}
}
