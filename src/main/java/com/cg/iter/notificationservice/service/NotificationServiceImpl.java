package com.cg.iter.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.cg.iter.notificationservice.entity.NotificationRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
    private JavaMailSender javaMailSender;
	
	
	
	
	@Override
	public void sendWelcomeMail(NotificationRequest notificationRequest) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
    

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(notificationRequest.getEmail());

        helper.setSubject("Congratulations you have successfully registered to CapStore.");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1 style=color:green>Hi, "+notificationRequest.getUsername()+" </h1>"
        		+ "<h3>Welcome to CapStore</h3>"
        		+ "<img src=\"https://i.ibb.co/Jdpt4N6/cgad.jpg\" alt=\"CG\" width=\"100%\" height=\"100%\">"
        		+ "<hr>"
        		+ "You will get our new offers on : <b>"+notificationRequest.getPhoneno()+"</b> <br/>"
        		+ "<p >To confirm your account, please click here : "
        		+ "http://localhost:9005/app/auth/confirmAccount?confirmationToken="+notificationRequest.getConfirmationToken()+" </p>", true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        helper.addAttachment("CapStore.png", 
        		new ClassPathResource("cgstore.png"));

        javaMailSender.send(msg);

    }
	
	
}
