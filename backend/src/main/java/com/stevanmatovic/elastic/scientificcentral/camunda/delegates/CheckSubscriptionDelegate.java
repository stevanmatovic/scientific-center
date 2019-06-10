package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.service.JournalService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckSubscriptionDelegate implements JavaDelegate {

  @Autowired
  private UserService userService;
  @Autowired
  private JournalService journalService;


  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    String username = (String)delegateExecution.getVariable("author");
    String journalName = (String)delegateExecution.getVariable("journalName");
    User user = userService.findByUsername(username);
    UserData userData = userService.getUserData(user);
    boolean subscribed = journalService.isSubscribed(journalName,userData);
    delegateExecution.setVariable("subscribed",subscribed);
  }
}
