package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.service.EmailService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotRelevantDelegate implements JavaDelegate{

  @Autowired
  EmailService emailService;

  @Autowired
  UserService userService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String a = (String) execution.getVariable("author");

    UserData author = userService.getUserDataByUsername(a);

    emailService.sendSimpleMessage(author.getEmail(), "Paper rejected",
        "Your paper is not relevant for this journal!");
  }

}
