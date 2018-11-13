//package cloud.service.classroom.email;
//
//import java.util.Properties;
//
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
//@RestController  
//@RequestMapping("mail")  
//public class MailController {
//	
//	 @Autowired  
//	 JavaMailSender mailSender;  
//	    
//	    @RequestMapping("sendemail")  
//	   public Object sendEmail(String  email)  
//	    {  
//	        try 
//	        {   
//	           final MimeMessage mimeMessage = this.mailSender.createMimeMessage();  
//	           final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);  
//	            message.setFrom("fightiforever@126.com");  
//	            message.setTo(email);  
//	           message.setSubject("密码重置验证码");  
//	            message.setText("尊敬的用户，点击此链接——，跳转到密码重置页为您重置密码。");  
//	            this.mailSender.send(mimeMessage);  
//	            return "success" ;  
//	       }  
//	       catch(Exception ex)  
//	        {   
//	           return "fail" ;  
//	        }  
//	    }  
//
//
//}
