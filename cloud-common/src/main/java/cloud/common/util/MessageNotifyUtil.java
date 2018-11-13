package cloud.common.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * ajax异步返回信息
 * 
 * 
 *
 */
public class MessageNotifyUtil
{

	// 成功 ： 200 ， 201 失败 ， 其他自定义
	private int code;// 状态码

	private String msg;// 提示的信息

	private Map<String, Object> appendMsg;// 附加的信息

	private Object obj; // 可以选择用appendMsg 或 obj

	public MessageNotifyUtil()
	{
		this(200, null, null, null);
	}

	public MessageNotifyUtil(int code)
	{
		this(code, null, null, null);
	}

	public MessageNotifyUtil(int code, String msg)
	{
		this(code, msg, null, null);
	}

	public MessageNotifyUtil(int code, Map<String, Object> appendMsg)
	{
		this(code, null, appendMsg, null);
	}

	public MessageNotifyUtil(int code, String msg, Map<String, Object> appendMsg)
	{
		this(code, msg, appendMsg, null);
	}

	public MessageNotifyUtil(int code, String msg, Map<String, Object> appendMsg, Object obj)
	{
		this.code = code;
		this.msg = msg;
		this.appendMsg = appendMsg;
		this.obj = obj;
	}

	/**
	 * 
	 * @Title: setMsg @Description: TODO(设置返回参数) @param @param
	 *         isTure @param @param sucMsg 成功时的返回信息 @param @param failMsg
	 *         失败时的返回信息 @return void 返回类型 @throws
	 */
	public void setMsg(boolean isTure, String sucMsg, String failMsg)
	{
		if (isTure)
		{
			setCode(200);
			setMsg(sucMsg);
		} else
		{
			setCode(201);
			setMsg(failMsg);
		}
	}

	public Map<String, Object> getAppendMsg()
	{
		return appendMsg;
	}

	public void setAppendMsg(Map<String, Object> appendMsg)
	{
		this.appendMsg = appendMsg;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public Object getObj()
	{
		return obj;
	}

	public void setObj(Object obj)
	{
		this.obj = obj;
	}

	/**
	 * 
	 * <b>方法描述：</b>提供安全的方法,把字符串解析为消息. 注意, 消息中的obj未被解析为对象其是JSONObject对象.<br/>
	 * <b>参数描述：</b><br/>
	 * 
	 * @param str
	 * @return code 404:字符串为空, code 201:解析出错<br/>
	 * @return MessageNotifyUtil<br/>
	 * @author lym
	 */
	public static MessageNotifyUtil parse(String str)
	{
		if (StringUtils.isBlank(str))
		{
			return new MessageNotifyUtil(404);
		}
		try
		{
			return JSON.parseObject(str, MessageNotifyUtil.class);
		} catch (Exception e)
		{
			e.printStackTrace();
			return new MessageNotifyUtil(201);
		}
	}

	/**
	 * 
	 * <b>方法描述：</b>提供安全的方法,把字符串解析为消息.<br/>
	 * <b>参数描述：</b><br/>
	 * 
	 * @param str
	 * @param clazz
	 *            message中obj的类型
	 * @return<br/>
	 * @return MessageNotifyUtil<br/>
	 */
	public static MessageNotifyUtil parse(String str, Class<?> clazz)
	{
		if (StringUtils.isBlank(str))
		{
			return new MessageNotifyUtil(404);
		}
		try
		{
			MessageNotifyUtil message = JSON.parseObject(str, MessageNotifyUtil.class);
			message.setObj(JSON.toJavaObject((JSON) message.getObj(), clazz));
			return message;
		} catch (Exception e)
		{
			e.printStackTrace();
			return new MessageNotifyUtil(201);
		}
	}
}