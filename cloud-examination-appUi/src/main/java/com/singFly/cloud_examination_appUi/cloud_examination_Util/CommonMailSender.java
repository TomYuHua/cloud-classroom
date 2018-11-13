package com.singFly.cloud_examination_appUi.cloud_examination_Util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.alibaba.fastjson.JSONObject;


public class CommonMailSender {
	
	private final static String mailHost="smtp.qq.com";

	private final static String mailPort="587";

	private final static String mailUsername="376213847@qq.com";

	private final static String mailPassword="lvqxtyzmkvtqbjbd";
	

	public static JSONObject sendEmail(String email,String checkCode)
	{

		JSONObject jsonObject = new JSONObject();
		try
		{
			// 创建Properties 类用于记录邮箱的一些属性
			final Properties props = new Properties();
			// 表示SMTP发送邮件，必须进行身份验证
			props.put("mail.smtp.auth", "true");
			// 此处填写SMTP服务器
			props.put("mail.smtp.host", mailHost.trim());
			// 端口号，QQ邮箱给出了两个端口
			props.put("mail.smtp.port", mailPort.trim());
			// 此处填写你的账号
			props.put("mail.user", mailUsername.trim());
			// 此处的密码就是16位STMP口令
			props.put("mail.password", mailPassword.trim());

			// 构建授权信息，用于进行SMTP进行身份验证
			Authenticator authenticator = new Authenticator()
			{

				protected PasswordAuthentication getPasswordAuthentication()
				{
					// 用户名、密码
					String userName = props.getProperty("mail.user");
					String password = props.getProperty("mail.password");
					return new PasswordAuthentication(userName, password);
				}
			};
			// 使用环境属性和授权信息，创建邮件会话
			Session mailSession = Session.getInstance(props, authenticator);
			// 创建邮件消息
			MimeMessage message = new MimeMessage(mailSession);
			// 设置发件人
			InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
			message.setFrom(form);
			// 设置收件人的邮箱
			InternetAddress to = new InternetAddress(email.trim());
			message.setRecipient(RecipientType.TO, to);
			// 设置邮件标题
			message.setSubject("获取验证码邮件");
			message.setContent("尊敬的用户，您的验证码为" + checkCode, "text/html;charset=UTF-8");
			// 最后当然就是发送邮件啦
			Transport.send(message);
			jsonObject.put("result", "success");

		} catch (Exception ex)
		{
			ex.printStackTrace();
			jsonObject.put("result", "fail");
		}
		return jsonObject;
	}
	


}
