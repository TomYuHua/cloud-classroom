package cloud.common.annotation;

import java.lang.annotation.*;


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
