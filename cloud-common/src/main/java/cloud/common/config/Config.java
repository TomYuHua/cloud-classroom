package cloud.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 读Properties属性文件
 * 
 * 
 *
 */
public class Config
{

	private static Log logger = LogFactory.getLog(Config.class);
	private Properties prop = null;
	// 单态控
	private static Config config = null;

	/**
	 * 加载配置文件
	 * 
	 * @throws IOException
	 */
	public void init(String fileName) throws IOException
	{
		InputStream in = null;
		try
		{
			in = Config.class.getResourceAsStream(fileName);
			prop = new Properties();
			prop.load(in);
		} catch (Exception e)
		{
			logger.error("load加载配置文件信息异常！", e);
			e.printStackTrace();
		} finally
		{
			if (in != null)
			{
				in.close();
			}
		}
	}

	/**
	 * 获取指定属性的值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key)
	{
		String value = prop.getProperty(key);
		return value;
	}

	/**
	 * 单态控制
	 * 
	 * @return
	 */
	public synchronized static Config getInstance(String fileName)
	{
		if (null == config)
		{
			config = new Config();
			try
			{
				config.init(fileName);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return config;
	}

	public Properties getProp()
	{
		return prop;
	}

	public void setProp(Properties prop)
	{
		this.prop = prop;
	}

}