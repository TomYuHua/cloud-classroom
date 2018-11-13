package com.singFly.cloud_examination_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@Configuration
public class DataSourceConfig
{
	private final static String WRITE_DATASOURCE_KEY = "writeDataSource";
	private final static String READ1_DATASOURCE_KEY = "read1DataSource";
	private final static String READ2_DATASOURCE_KEY = "read2DataSource";
	/*
	 * private final static String MASTER_DATASOURCE_KEY = "masterDataSource";
	 * private final static String CLUSTER_DATASOURCE_KEY = "clusterDataSource";
	 * private final static String CLUSTER1_DATASOURCE_KEY = "masterDataSource";
	 */

	/**
	 * 注入AbstractRoutingDataSource
	 * 
	 * @param writeDataSource
	 * @param read1DataSource
	 * @param read2DataSource
	 * @param masterDataSource
	 * @param clusterDataSource
	 * @param cluster1DataSource
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AbstractRoutingDataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
			@Qualifier("read1DataSource") DataSource read1DataSource,
			@Qualifier("read2DataSource") DataSource read2DataSource) throws Exception
	// @Qualifier("masterDataSource") DataSource masterDataSource,
	// @Qualifier("clusterDataSource") DataSource clusterDataSource,
	// @Qualifier("clusterDataSource1") DataSource cluster1DataSource) throws
	// Exception
	{
		DynamicDataSource dataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap();
		targetDataSources.put(WRITE_DATASOURCE_KEY, writeDataSource);
		targetDataSources.put(READ1_DATASOURCE_KEY, read1DataSource);
		targetDataSources.put(READ2_DATASOURCE_KEY, read2DataSource);

		/*
		 * targetDataSources.put(MASTER_DATASOURCE_KEY, masterDataSource);
		 * targetDataSources.put(CLUSTER_DATASOURCE_KEY, clusterDataSource);
		 * targetDataSources.put(CLUSTER1_DATASOURCE_KEY, cluster1DataSource);
		 */

		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(writeDataSource);
		return dataSource;
	}
}
