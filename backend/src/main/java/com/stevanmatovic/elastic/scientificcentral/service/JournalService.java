package com.stevanmatovic.elastic.scientificcentral.service;

import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class JournalService {

  private JournalRepository journalRepository;

  @Autowired
  public JournalService(JournalRepository journalRepository) {
    this.journalRepository = journalRepository;
  }

  public List<Journal> getAll(){
    return journalRepository.findAll();
  }

  public boolean isSubscribed(String journalName,UserData data){
    Journal journal = journalRepository.findByTitle(journalName);
    if(!journal.isOpenAccess())
      return true;
    Set<UserData> subscribers =  journal.getSubscribers();
    for(UserData userData: subscribers){
      if(userData.getId() == data.getId())
        return true;
    }
    return false;
  }

  public void subscribe(String journalName, UserData userData) {
    Journal journal = journalRepository.findByTitle(journalName);
    journal.getSubscribers().add(userData);
    journalRepository.save(journal);
  }

  public Journal getByTitle(String title){
    return journalRepository.findByTitle(title);
  }
}
