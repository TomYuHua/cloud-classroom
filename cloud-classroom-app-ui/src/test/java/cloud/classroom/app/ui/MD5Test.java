package cloud.classroom.app.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cloud.classroom.app.ui.Util.MD5Util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MD5Test.class)
public class MD5Test
{
	private static Map users = new HashMap();

	/**
	 * 上传测试
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception
	{

		String userName = "zyg";
		String password = "123";

		registerUser(userName, password);

		userName = "changong";
		password = "456";
		registerUser(userName, password);

		String loginUserId = "zyg";
		String pwd = "1232";
		try
		{
			if (loginValid(loginUserId, pwd))
			{
				System.out.println("欢迎登陆！！！");
			} else
			{
				System.out.println("口令错误，请重新输入！！！");
			}
		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/**
	 * 注册用户
	 * 
	 * @param userName
	 * @param password
	 */
	public static void registerUser(String userName, String password)
	{
		String encryptedPwd = null;
		try
		{
			encryptedPwd = MD5Util.getEncryptedPwd(password);

			users.put(userName, encryptedPwd);

		} catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 验证登陆
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean loginValid(String userName, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String pwdInDb = (String) users.get(userName);
		if (null != pwdInDb)
		{ // 该用户存在
			return MD5Util.validPassword(password, pwdInDb);
		} else
		{
			System.out.println("不存在该用户！！！");
			return false;
		}
	}

}