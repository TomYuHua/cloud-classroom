package org.common.dfs.constant;

import java.io.File;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cloud.common.config.Config;

public class Constant
{
	/**
	 * 配置文件路径
	 */
	public final static String FDFS_CONFIG_PROP = "/fdfs/fdfs_client.properties";

	private final static String path = Constant.class.getResource(Constant.FDFS_CONFIG_PROP).getFile();

	/**
	 * 配置文件绝对路径
	 */
	private static Logger log = LoggerFactory.getLogger(Constant.class);

	private final static String decodePath = new File(path).getAbsolutePath();

	public static String CONF_FILE_PATH = Constant.urlDecode();

	private static String urlDecode()
	{

		try
		{
//			log.error("path......................" + path);
//			log.error("decodePath...................." + decodePath);
//			log.error(URLDecoder.decode(decodePath, "UTF-8"));
			// return URLDecoder.decode(decodePath, "UTF-8");

			return URLDecoder.decode(path, "UTF-8");
		} catch (Exception e)
		{
			return "";
		}

	}

	/**
	 * 连接池最大连接数key
	 */
	public static final String MAX_TOTAL_KEY = "max_total";

	/**
	 * 连接池最大连接数
	 */
	public static final int MAX_TOTAL = Integer.parseInt(Config.getInstance(Constant.FDFS_CONFIG_PROP).getValue(Constant.MAX_TOTAL_KEY).trim());

}
