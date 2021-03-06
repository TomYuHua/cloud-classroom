package cloud.service.classroom.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = { "cloud.service.classroom.dao.cluster1.mapper" }, sqlSessionFactoryRef = "cluster1SqlSessionFactory")
public class Cluster1DataSourceConfig
{

	@Value("${spring.datasource.cluster1.clusterMapperLocations}")
	private String cluster1MapperLocations;

	@ConfigurationProperties(prefix = "spring.datasource.cluster1")
	@Bean(name = "cluster1DataSource")
	public DataSource cluster1DataSource()
	{
		return new DruidDataSource();
	}

	/**
	 * SqlSessionFactory配置
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "cluster1SqlSessionFactory")
	public SqlSessionFactory cluster1SqlSessionFactory(@Qualifier("cluster1DataSource") DataSource dataSource) throws Exception
	{

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		// 配置mapper文件位置
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(cluster1MapperLocations));

		// 配置分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "count=countSql");
		pageHelper.setProperties(properties);

		// 设置插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageHelper });
		return sqlSessionFactoryBean.getObject();
	}

	/*
	 * 配置事物管理器
	 *
	 * @return
	 */
	@Bean(name = "cluster1TransactionManager")
	public DataSourceTransactionManager cluster1TransactionManager(@Qualifier("cluster1DataSource") DataSource dataSource)
	{
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource);
		return dataSourceTransactionManager;
	}
}
