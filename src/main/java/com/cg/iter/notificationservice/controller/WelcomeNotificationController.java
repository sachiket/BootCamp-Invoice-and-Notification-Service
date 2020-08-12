package com.cg.iter.notificationservice.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iter.notificationservice.entity.NotificationRequest;
import com.cg.iter.notificationservice.service.InvoiceGeneratorService;
import com.cg.iter.notificationservice.service.NotificationService;

@RestController
@RequestMapping("/welcomeMessage")
public class WelcomeNotificationController {

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	InvoiceGeneratorService invoiceGeneratorService;
	
	
	@PostMapping("/send")
	public String welcomeMessage(@RequestBody NotificationRequest notificationRequest) {
		
		try {
			notificationService.sendWelcomeMail(notificationRequest);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "welcome mail sent!!";
	}
	
	
}
