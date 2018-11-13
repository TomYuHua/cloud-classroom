package cloud.classroom.app.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class Mail
{
	@Autowired
	private JavaMailSender mailSender;
	// @Autowired
	// private VelocityEngine velocityEngine;

	public void sendSimpleMail() throws Exception
	{

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("2579118129@qq.com");
		message.setTo("2579118129@qq.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");

		mailSender.send(message);
	}

	public void sendAttachmentsMail() throws Exception
	{

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("2579118129@qq.com");
		helper.setTo("2579118129@qq.com");
		helper.setSubject("主题：有附件");
		helper.setText("有附件的邮件");

		FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
		helper.addAttachment("附件-1.jpg", file);
		helper.addAttachment("附件-2.jpg", file);

		mailSender.send(mimeMessage);
	}

	public void sendInlineMail() throws Exception
	{

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("2579118129@qq.com");
		helper.setTo("2579118129@qq.com");
		helper.setSubject("主题：嵌入静态资源");
		helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);

		FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
		helper.addInline("weixin", file);

		mailSender.send(mimeMessage);
	}

	public void sendTemplateMail() throws Exception
	{

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("2579118129@qq.com");
		helper.setTo("2579118129@qq.com");
		helper.setSubject("主题：模板邮件");

		Map<String, Object> model = new HashMap();
		model.put("username", "didi");
		// String text =
		// VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
		// "template.vm", "UTF-8", model);
		// helper.setText(text, true);

		mailSender.send(mimeMessage);
	}

}
