package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import com.stevanmatovic.elastic.scientificcentral.service.JournalService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChooseChiefEditorDelegate implements JavaDelegate {

  @Autowired
  JournalService journalService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    String journalName = (String)execution.getVariable("journalName");
    Journal journal = journalService.getByTitle(journalName);
    String username = journal.getMainEditor().getUser().getUsername();
    execution.setVariable("chiefEditor",username);
  }
}
