package cloud.classroom.app.ui.Interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
// @ComponentScan("cloud.classroom.app.ui")
// @EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	// @Bean
	// public UrlBasedViewResolver viewResolver()
	// {
	// UrlBasedViewResolver resolver = new UrlBasedViewResolver();
	//// resolver.setPrefix("/WEB-INF/views/");
	//// resolver.setSuffix(".jsp");
	//// resolver.setViewClass(JstlView.class);
	// return resolver;
	// }

	// 优先级 ：META-INFO/resources > resources > static > public
	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry)
	// {
	//
	// // addResourceLocations指的是文件放置的目录，addResoureHandler指的是对外暴露的访问路径
	// registry.addResourceHandler("/").addResourceLocations("classpath:/static/");
	// super.addResourceHandlers(registry);
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new AppInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**")
				.excludePathPatterns("/userui/loginPage").excludePathPatterns("/");
		// registry.addInterceptor(appInterceptorAdapter());

		super.addInterceptors(registry);
	}

	// 自定义拦截器
	@Bean
	public AppInterceptorAdapter appInterceptorAdapter()
	{
		return new AppInterceptorAdapter();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		// registry.addViewController("/").setViewName("Index");
		// registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
}
