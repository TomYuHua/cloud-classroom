package cloud.common.helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.common.dfs.fastdfs.TrackerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHelper
{
	private static Logger log = LoggerFactory.getLogger(HttpHelper.class);
	// protected final static Logger log = Logger.getLogger(HttpHelper.class);
	public static final String Referer = "xgfapp";

	// 定义编码格式 UTF-8
	public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";
	// 定义编码格式 GBK
	// public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";
	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final String EMPTY = "";
	private static final String HEAD = "HEAD";

	private static int connectionTimeOut = 60000;
	private static int socketTimeOut = 30000;
	private static int maxConnectionPerHost = 20;
	private static int maxTotalConnections = 20;
	private static HttpClient client;

	private static MultiThreadedHttpConnectionManager connectionManager = null;

	static
	{
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		connectionManager.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, URL_PARAM_DECODECHARSET_UTF8);
		client = new HttpClient(connectionManager);
	}

	/**
	 * 判断是否存在当前连接   
	 * 
	 * @param url
	 *            待请求的URL
	 */
	public static boolean URLConnectExists(String url)
	{
		boolean isExist = false;
		Map<String, List<String>> headerMap = null;
		HttpURLConnection con = null;
		try
		{
			// 设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
			HttpURLConnection.setFollowRedirects(false);
			// 到 URL 所引用的远程对象的连接
			con = (HttpURLConnection) new URL(url).openConnection();

			// con.setConnectTimeout(500);
			con.setReadTimeout(1800);
			/*
			 * 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE
			 * 以上方法之一是合法的，具体取决于协议的限制。
			 */
			con.setRequestMethod(HEAD);
			// 从 HTTP 响应消息头
			headerMap = con.getHeaderFields();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		} finally
		{
			if (headerMap != null && headerMap.size() > 0)
			{
				for (String key : headerMap.keySet())
				{
					if (key == null)
					{
						List<String> list = headerMap.get(key);
						if (list != null && list.size() > 0)
						{
							String sc_msg = headerMap.get(key).get(0);
							if (sc_msg.contains(Integer.toString(HttpStatus.SC_OK)))
							{
								isExist = true;
								break;
							}
						}
					}
				}
			}
		}
		return isExist;

	}

	/**
	 * POST方式提交数据StringRequestEntity
	 * 
	 * @param url
	 *            待请求的URL
	 * @param paramJson
	 *            json格式 要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果，逻辑处理过程中通过LOGGER进行信息捕获作为日志跟踪
	 * @throws IOException
	 *             IO异常
	 */
	public static String URLPost(String url, String paramJson, String enc)
	{
		String response = EMPTY;
		PostMethod postMethod = null;
		try
		{
			log.info("httpClient:" + url);
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "application/json;charset=" + enc);
			postMethod.addRequestHeader("Referer", Referer);
			// 将表单的值放入postMethod中
			RequestEntity entity = new StringRequestEntity(paramJson, "application/json", enc);
			log.info("this is request json:" + paramJson);
			// @SuppressWarnings("deprecation")
			postMethod.setRequestEntity(entity);

			// log.info("posrtMethod uri:" + postMethod.getURI());
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			// log.info("statusCode:" + statusCode);
			if (statusCode == HttpStatus.SC_OK)
			{
				String responseString = new String(
						postMethod.getResponseBodyAsString().getBytes(URL_PARAM_DECODECHARSET_UTF8));
				log.info("TEST INFO reponse string :" + responseString);
				response = new String(postMethod.getResponseBodyAsString().getBytes(URL_PARAM_DECODECHARSET_UTF8),
						URL_PARAM_DECODECHARSET_UTF8);
				// return postMethod.getResponseBodyAsString();
			} else
			{
				// 响应状态码
				log.error("Response status code is " + postMethod.getStatusCode());
			}
		} catch (HttpException e)
		{
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			log.error("A fatal exception may be a problem in which an agreement is not or is returned.", e);
			e.printStackTrace();
		} catch (IOException e)
		{
			// 发生网络异常
			log.error("Network anomaly.", e);
			e.printStackTrace();
		} finally
		{
			if (postMethod != null)
			{
				postMethod.releaseConnection();
				postMethod = null;
			}
		}

		return response;
	}

	/**
	 * POST方式提交数据StringRequestEntity
	 * 
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果，逻辑处理过程中通过LOGGER进行信息捕获作为日志跟踪
	 * @throws IOException
	 *             IO异常
	 */
	public static String URLPost(String url, Map<String, String> params, String enc)
	{
		String response = EMPTY;
		PostMethod postMethod = null;
		try
		{
			log.info("httpClient:" + url);
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			postMethod.addRequestHeader("Referer", Referer);
			// 将表单的值放入postMethod中
			if (params != null)
			{
				Set<String> keySet = params.keySet();
				for (String key : keySet)
				{
					String value = params.get(key);
					postMethod.addParameter(key, value);
				}
			}

			// log.info("posrtMethod uri:" + postMethod.getURI());
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			// log.info("statusCode:" + statusCode);
			if (statusCode == HttpStatus.SC_OK)
			{
				String responseString = new String(
						postMethod.getResponseBodyAsString().getBytes(URL_PARAM_DECODECHARSET_UTF8));
				log.info("TEST INFO reponse string :" + responseString);
				response = new String(postMethod.getResponseBodyAsString().getBytes(URL_PARAM_DECODECHARSET_UTF8),
						URL_PARAM_DECODECHARSET_UTF8);
				// return postMethod.getResponseBodyAsString();
			} else
			{
				// 响应状态码
				log.error("Response status code is " + postMethod.getStatusCode());
			}
		} catch (HttpException e)
		{
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			log.error("A fatal exception may be a problem in which an agreement is not or is returned.", e);
			e.printStackTrace();
		} catch (IOException e)
		{
			// 发生网络异常
			log.error("Network anomaly.", e);
			e.printStackTrace();
		} finally
		{
			if (postMethod != null)
			{
				postMethod.releaseConnection();
				postMethod = null;
			}
		}

		return response;
	}

	/**
	 * GET方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String URLGet(String url, Map<String, String> params, String enc)
	{
		String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);

		if (strtTotalURL.indexOf("?") == -1)
		{
			strtTotalURL.append(url).append("?").append(getUrl(params, enc));
		} else
		{
			strtTotalURL.append(url).append("&").append(getUrl(params, enc));
		}
		// log.info("GET URL = \n" + strtTotalURL.toString());
		try
		{
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			getMethod.addRequestHeader("Referer", Referer);
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK)
			{
				response = getMethod.getResponseBodyAsString();
			} else
			{
				// 响应状态码
				log.info(" Response status code is " + getMethod.getStatusCode());
			}
		} catch (HttpException e)
		{
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			log.error("A fatal exception may be a problem in which an agreement is not or is returned.", e);
			e.printStackTrace();
		} catch (IOException e)
		{
			// 发生网络异常
			log.error("Network anomaly.", e);
			e.printStackTrace();
		} finally
		{
			if (getMethod != null)
			{
				getMethod.releaseConnection();
				getMethod = null;
			}
		}

		return response;
	}

	/**
	 * 据Map生成URL字符串
	 * 
	 * @param map
	 *            Map
	 * @param valueEnc
	 *            URL编码
	 * @return URL
	 */
	private static String getUrl(Map<String, String> map, String valueEnc)
	{
		if (null == map || map.keySet().size() == 0)
		{
			return (EMPTY);
		}

		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();)
		{
			String key = it.next();
			if (map.containsKey(key))
			{
				String val = map.get(key);
				String str = val != null ? val : EMPTY;
				try
				{
					str = URLEncoder.encode(str, valueEnc);
				} catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = EMPTY;
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1)))
		{
			strURL = strURL.substring(0, strURL.length() - 1);
		}

		return (strURL);
	}

	@SuppressWarnings("deprecation")
	public static String getBackValue(String sendUrl, String param)
	{
		String temp = null, str = "";

		try
		{
			URL httpurl = new URL(sendUrl);
			URLConnection connection = (URLConnection) httpurl.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("ContentType", "text/xml;charset=utf-8");
			// connection.setRequestProperty("User-Agent", "Mozilla/4.0
			// (compatible; MSIE 5.0; Windows NT; DigExt)");

			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out.write(param);
			out.flush();
			out.close();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			while ((temp = in.readLine()) != null)
			{
				str += temp + "\r\n";
			}
			in.close();
		} catch (IOException ex2)
		{
			System.out.println(" exe IOException:" + ex2.toString());
		}
		try
		{
			str = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return str;
	}

	@SuppressWarnings("deprecation")
	public static String getBackValueThrows(String sendUrl, String param) throws IOException
	{
		String temp = null, str = "";
		URL httpurl = new URL(sendUrl);
		URLConnection connection = (URLConnection) httpurl.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("ContentType", "text/xml;charset=utf-8");
		// connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible;
		// MSIE 5.0; Windows NT; DigExt)");

		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
		out.write(param);
		out.flush();
		out.close();
		DataInputStream in = new DataInputStream(connection.getInputStream());
		while ((temp = in.readLine()) != null)
		{
			str += temp + "\r\n";
		}
		in.close();
		str = new String(str.getBytes("ISO-8859-1"), "utf-8");
		return str;
	}

}
