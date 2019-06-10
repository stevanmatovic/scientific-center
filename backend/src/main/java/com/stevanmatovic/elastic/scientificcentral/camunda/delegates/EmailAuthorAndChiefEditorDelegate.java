package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.service.EmailService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailAuthorAndChiefEditorDelegate implements JavaDelegate{

  @Autowired
  EmailService emailService;

  @Autowired
  UserService userService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String e = (String)execution.getVariable("chiefEditor");
    String a = (String)execution.getVariable("author");

    UserData editor = userService.getUserDataByUsername(e);
    UserData author = userService.getUserDataByUsername(a);

    emailService.sendSimpleMessage(editor.getEmail(),"New paper submission",
        "New paper has been submitted by " + author.getName() + " " + author.getLastName());

    emailService.sendSimpleMessage(author.getEmail(),"Paper submission",
        "Your paper has been submitted successfully!");
  }
}
