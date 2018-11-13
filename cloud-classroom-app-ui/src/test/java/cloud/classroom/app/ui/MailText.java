package cloud.classroom.app.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cloud.classroom.app.ui.Util.MD5Util;
import cloud.classroom.app.ui.Util.MailCommon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MD5Test.class)
public class MailText
{
	private static Map users = new HashMap();

	@Value("${spring.mail.host}")
	private String mailHost;
	@Value("${spring.mail.port}")
	private String mailPort;
	@Value("${spring.mail.username}")
	private String mailUsername;
	@Value("${spring.mail.password}")
	private String mailPassword;

	/**
	 * 上传测试
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws MessagingException, IOException
	{

		Map<String, String> map = new HashMap<String, String>();
		MailCommon mail = new MailCommon(mailUsername, mailPassword);

		map.put("mail.smtp.auth", "true");
		// 此处填写SMTP服务器
		map.put("mail.smtp.host", mailHost.trim());
		// 端口号，QQ邮箱给出了两个端口
		map.put("mail.smtp.port", mailPort.trim());
		// // 此处填写你的账号
		// map.put("mail.user", mailUsername.trim());
		// // 此处的密码就是16位STMP口令
		// map.put("mail.password", mailPassword.trim());

		mail.setPros(map);
		mail.initMessage();
		/*
		 * 添加收件人有三种方法： 1,单人添加(单人发送)调用setRecipient(str);发送String类型
		 * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
		 * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
		 */
		List<String> list = new ArrayList<String>();
		list.add("2579118129@qq.com");
		list.add("157161592@qq.com");
		list.add("hyc_happy_everyday@163.com");
		mail.setRecipients(list);
		/*
		 * String defaultStr =
		 * "1221111@qq.com,11111@qq.com,22222@qq.com,3333@qq. com; StringBuffer
		 * sb = new StringBuffer(); sb.append(defaultStr);
		 * sb.append(",316121113@qq.com"); mail.setRecipients(sb);
		 */
		mail.setSubject("测试邮箱");
		// mail.setText("谢谢合作");
		mail.setDate(new Date());
		mail.setFrom("MY");
		// mail.setMultipart("D:你你你.txt");
		mail.setContent("谢谢合作", "text/html; charset=UTF-8");
		/*
		 * List<String> fileList = new ArrayList<String>();
		 * fileList.add("D:aaaasd.jpg"); fileList.add("D:aasdn.zip");
		 * fileList.add("D:dasdasz.sql"); fileList.add("D:阿斯顿发.doc");
		 * mail.setMultiparts(fileList);
		 */
		System.out.println(mail.sendMessage());
	}

}