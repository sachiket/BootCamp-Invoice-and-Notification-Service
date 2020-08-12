package com.cg.iter.notificationservice.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.cg.iter.notificationservice.entity.NotificationRequest;

public interface NotificationService {
	
	public void sendWelcomeMail(NotificationRequest notificationRequest) throws MessagingException, IOException;
	
}
