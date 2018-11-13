package com.singFly.cloud_examination_common_aspect;

//@Aspect
//@Component
public class DynamicDataSourceAspect {
//	@Around("execution(public * cloud.service.classroom.services..*.*(..))")
//	public Object around(ProceedingJoinPoint pjp) throws Throwable
//	{
//		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//		Method targetMethod = methodSignature.getMethod();
//		if (targetMethod.isAnnotationPresent(TargetDataSource.class))
//		{
//			String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).dataSource();
//			System.out.println("----------数据源是:" + targetDataSource + "------");
//			DynamicDataSourceHolder.setDataSource(targetDataSource);
//		}
//		Object result = pjp.proceed();// 执行方法
//		return result;
//	}
}
