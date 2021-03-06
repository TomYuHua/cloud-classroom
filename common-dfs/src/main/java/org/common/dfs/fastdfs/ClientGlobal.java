package org.common.dfs.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.common.dfs.common.IniFileReader;
import org.common.dfs.common.MyException;

/**
 * Global variables
 * 
 * @author Happy Fish / YuQing
 * @version Version 1.11
 */
public class ClientGlobal
{

	public static int g_connect_timeout; // millisecond
	public static int g_network_timeout; // millisecond
	public static String g_charset;
	public static int g_tracker_http_port;
	public static boolean g_anti_steal_token; // if anti-steal token
	public static String g_secret_key; // generage token secret key
	public static TrackerGroup g_tracker_group;

	public static final int DEFAULT_CONNECT_TIMEOUT = 10; // second
	public static final int DEFAULT_NETWORK_TIMEOUT = 60; // second

	public static int section_size;
	public static final int DEFAULT_SECTION_SIZE = 1024; // kb

	public static int ingore_diff_time;// s
	public static final int DEFAULT_INGORE_DIFF_TIME = 10; // s

	private ClientGlobal()
	{
	}

	/**
	 * load global variables
	 * 
	 * @param conf_filename
	 *            config filename
	 */
	public static void init(String conf_filename) throws FileNotFoundException, IOException, MyException
	{
		IniFileReader iniReader = null;
		String[] szTrackerServers;
		String[] parts;

		try
		{
			iniReader = new IniFileReader(conf_filename);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		g_connect_timeout = iniReader.getIntValue("connect_timeout", DEFAULT_CONNECT_TIMEOUT);
		if (g_connect_timeout < 0)
		{
			g_connect_timeout = DEFAULT_CONNECT_TIMEOUT;
		}
		g_connect_timeout *= 1000; // millisecond

		g_network_timeout = iniReader.getIntValue("network_timeout", DEFAULT_NETWORK_TIMEOUT);
		if (g_network_timeout < 0)
		{
			g_network_timeout = DEFAULT_NETWORK_TIMEOUT;
		}
		g_network_timeout *= 1000; // millisecond

		g_charset = iniReader.getStrValue("charset");
		if (g_charset == null || g_charset.length() == 0)
		{
			g_charset = "ISO8859-1";
		}

		// 配置文件传入的为kb，转化为字节数
		section_size = iniReader.getIntValue("section_size", DEFAULT_SECTION_SIZE);
		if (section_size < 0)
		{
			section_size = DEFAULT_SECTION_SIZE;
		}
		section_size *= 1024;

		ingore_diff_time = iniReader.getIntValue("ingore_diff_time", DEFAULT_INGORE_DIFF_TIME);
		if (ingore_diff_time < 0)
		{
			ingore_diff_time = DEFAULT_INGORE_DIFF_TIME;
		}
		ingore_diff_time *= 1000;

		szTrackerServers = iniReader.getValues("tracker_server");
		if (szTrackerServers == null)
		{
			throw new MyException("item \"tracker_server\" in " + conf_filename + " not found");
		}

		InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
		for (int i = 0; i < szTrackerServers.length; i++)
		{
			parts = szTrackerServers[i].split("\\:", 2);
			if (parts.length != 2)
			{
				throw new MyException(
						"the value of item \"tracker_server\" is invalid, the correct format is host:port");
			}
			tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
		}
		g_tracker_group = new TrackerGroup(tracker_servers);

		g_tracker_http_port = iniReader.getIntValue("http.tracker_http_port", 80);
		g_anti_steal_token = iniReader.getBoolValue("http.anti_steal_token", false);
		if (g_anti_steal_token)
		{
			g_secret_key = iniReader.getStrValue("http.secret_key");
		}
	}

	/**
	 * construct Socket object
	 * 
	 * @param ip_addr
	 *            ip address or hostname
	 * @param port
	 *            port number
	 * @return connected Socket object
	 */
	public static Socket getSocket(String ip_addr, int port) throws IOException
	{
		Socket sock = new Socket();
		sock.setSoTimeout(ClientGlobal.g_network_timeout);
		sock.connect(new InetSocketAddress(ip_addr, port), ClientGlobal.g_connect_timeout);
		return sock;
	}

	/**
	 * construct Socket object
	 * 
	 * @param addr
	 *            InetSocketAddress object, including ip address and port
	 * @return connected Socket object
	 */
	public static Socket getSocket(InetSocketAddress addr) throws IOException
	{
		Socket sock = new Socket();
		sock.setSoTimeout(ClientGlobal.g_network_timeout);
		sock.connect(addr, ClientGlobal.g_connect_timeout);
		return sock;
	}

	public static int getG_connect_timeout()
	{
		return g_connect_timeout;
	}

	public static void setG_connect_timeout(int connect_timeout)
	{
		ClientGlobal.g_connect_timeout = connect_timeout;
	}

	public static int getG_network_timeout()
	{
		return g_network_timeout;
	}

	public static void setG_network_timeout(int network_timeout)
	{
		ClientGlobal.g_network_timeout = network_timeout;
	}

	public static String getG_charset()
	{
		return g_charset;
	}

	public static void setG_charset(String charset)
	{
		ClientGlobal.g_charset = charset;
	}

	public static int getG_tracker_http_port()
	{
		return g_tracker_http_port;
	}

	public static void setG_tracker_http_port(int tracker_http_port)
	{
		ClientGlobal.g_tracker_http_port = tracker_http_port;
	}

	public static boolean getG_anti_steal_token()
	{
		return g_anti_steal_token;
	}

	public static boolean isG_anti_steal_token()
	{
		return g_anti_steal_token;
	}

	public static void setG_anti_steal_token(boolean anti_steal_token)
	{
		ClientGlobal.g_anti_steal_token = anti_steal_token;
	}

	public static String getG_secret_key()
	{
		return g_secret_key;
	}

	public static void setG_secret_key(String secret_key)
	{
		ClientGlobal.g_secret_key = secret_key;
	}

	public static TrackerGroup getG_tracker_group()
	{
		return g_tracker_group;
	}

	public static void setG_tracker_group(TrackerGroup tracker_group)
	{
		ClientGlobal.g_tracker_group = tracker_group;
	}
}