package cloud.classroom.app.ui.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

//使用@Aspect注解将一个java类定义为切面类

/**
 * 如何使用AOP统一记录web请求日志
 * 基本思路是通过aop去切web层的controller实现，获取每个http的内容并通过log4j将日志内容写到应用服务器的文件系统中
 *
 * 
 * 
 * 通过@Pointcut定义的切入点为 cloud.classroom.app.ui.Controller包下的所有函数（对Controller层所有请求处理做切入点），
 * 然后通过@Before实现，对请求内容的日志记录 ，最后通过@AfterReturning记录请求返回的对象
 * 
 * 
 * 
 * 切面的处理顺序问题。 解决： 需要定义每个切面的优先级，我们需要@Order(i)注解来标识切面的优先级。 i的值越小，优先级越高。
 * 假设我们还有一个切面是CheckAspect用来校验name必须为aa，我们为其设置@Order(10)，而上文中WebLogAspect设置为@Order(5)，所以WebLogAspect有更高的优先级，
 * 
 * 这个时候执行顺序是这样的： 在@Before中优先执行@Order(5)的内容，再执行@Order(10)的内容
 * 在@After和@AfterReturning中优先执行@Order(10)的内容，再执行@Order(5)的内容
 * 
 * 所以我们可以这样子总结：
 * 
 * 在切入点前的操作，按order的值由小到大执行 在切入点后的操作，按order的值由大到小执行
 * 
 * 
 * 
 * 
 * 
 * 
 */
//@Aspect
//@Component
public class WebLogAspect
{
	private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

	// 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等
	@Pointcut("execution( * cloud.classroom.app.ui.Controller..*.*(..))")
	public void webLog()
	{
	}

	// 1111111111111
	ThreadLocal<Long> startTime = new ThreadLocal<>();

	// 使用@Before在切入点开始处切入内容
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable
	{
		// 222222222222
		startTime.set(System.currentTimeMillis());

		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable
	{
		// 3333333333333333333333
		logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
		// 处理完请求，返回内容
		logger.info("RESPONSE : " + ret);
	}

}
