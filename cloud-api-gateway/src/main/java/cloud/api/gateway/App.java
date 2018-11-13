package cloud.api.gateway;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import cloud.api.gateway.filter.AccessFilter;
import cloud.api.gateway.filter.AccessUserFiler;

/**
 * Hello world!
 *
 */
@EnableZuulProxy
@SpringCloudApplication
public class App
{
	public static void main(String[] args)
	{
		new SpringApplicationBuilder(App.class).web(true).run(args);
	}

	@Bean
	public AccessFilter accessFilter()
	{
		return new AccessFilter();
	}

	@Bean
	public AccessUserFiler  accessUserFilter()
	{
	
		return new AccessUserFiler();
	}
	
	
	
}
