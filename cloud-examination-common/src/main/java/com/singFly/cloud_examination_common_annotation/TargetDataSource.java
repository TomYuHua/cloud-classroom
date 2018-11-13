package com.singFly.cloud_examination_common_annotation;

/**
* 切换数据源
* @author Administrator
*
*/
//@Target({ ElementType.METHOD, ElementType.TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
public @interface TargetDataSource
{
	String dataSource() default "";// 数据源
}
