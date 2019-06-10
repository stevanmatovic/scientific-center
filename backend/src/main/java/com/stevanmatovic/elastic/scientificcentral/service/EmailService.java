package com.stevanmatovic.elastic.scientificcentral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;




@Service
public class EmailService {

  public JavaMailSender emailSender;


  @Autowired
  public EmailService(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Async
  public void sendSimpleMessage(String reciever, String subject, String content) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(reciever);
    message.setSubject(subject);
    message.setText(content);
    emailSender.send(message);
  }

}
