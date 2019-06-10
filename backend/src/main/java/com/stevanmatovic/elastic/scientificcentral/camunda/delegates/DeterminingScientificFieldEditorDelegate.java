package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.service.JournalService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeterminingScientificFieldEditorDelegate implements JavaDelegate {

  @Autowired
  UserService userService;

  @Autowired
  JournalService journalService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String field = (String) execution.getVariable("researchField");
    String journalName = (String) execution.getVariable("journalName");

    Journal journal = journalService.getByTitle(journalName);
    UserData fieldEditor = null;
    for (UserData user : journal.getEditors()){
      if(user.getResearchFields().equalsIgnoreCase(field))
        fieldEditor = user;
    }
    if(fieldEditor == null)
      execution.setVariable("fieldEditor", execution.getVariable("chiefEditor"));
    else
      execution.setVariable("fieldEditor", fieldEditor.getUser().getUsername());
  }

}
