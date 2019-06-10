package com.stevanmatovic.elastic.scientificcentral;

import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.repository.JournalRepository;
import com.stevanmatovic.elastic.scientificcentral.repository.UserDataRepository;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class Loader {





  @Autowired
  UserService userService;

  @Autowired
  UserDataRepository userDataRepository;

  @Autowired
  JournalRepository journalRepository;

  @PostConstruct
  public void load(){
    User author = new User(1L,"user","$2a$04$ZzZ96ci9J9xip5Rt0cFoeOCPKN2q2G7ERXru81PQTuKnNJ3GTPNRS","USER");
    User editor = new User(2L,"urednik","$2a$04$ZzZ96ci9J9xip5Rt0cFoeOCPKN2q2G7ERXru81PQTuKnNJ3GTPNRS","EDITOR");
    User editorOfField = new User(3L,"urednikOblasti","$2a$04$ZzZ96ci9J9xip5Rt0cFoeOCPKN2q2G7ERXru81PQTuKnNJ3GTPNRS","EDITOR");
    User r1 = new User(4L,"recezent1","$2a$04$ZzZ96ci9J9xip5Rt0cFoeOCPKN2q2G7ERXru81PQTuKnNJ3GTPNRS","REVIEWER");
    User r2 = new User(5L,"recezent2","$2a$04$ZzZ96ci9J9xip5Rt0cFoeOCPKN2q2G7ERXru81PQTuKnNJ3GTPNRS","REVIEWER");
    User r3 = new User(6L,"recezent3","$2a$04$ZzZ96ci9J9xip5Rt0cFoeOCPKN2q2G7ERXru81PQTuKnNJ3GTPNRS","REVIEWER");

    userService.save(author);
    userService.save(editor);
    userService.save(editorOfField);
    userService.save(r1);
    userService.save(r2);
    userService.save(r3);

    userDataRepository.save(new UserData(1L,"Jovan","Jovanovic","auhor@j.com","Novi Sad","Srbija","student","",author));
    userDataRepository.save(new UserData(2L,"Jovan","Jovanovic","editor@j.com","Novi Sad","Srbija","student","",editor));
    userDataRepository.save(new UserData(3L,"Jovan","Jovanovic","editorOfField@j.com","Novi Sad","Srbija","student","Computer Science",editorOfField));
    userDataRepository.save(new UserData(4L,"Jovan","Jovanovic","j@j.com","Novi Sad","Srbija","student","Computer Science",r1));
    userDataRepository.save(new UserData(5L,"Jovan","Jovanovic","j@j.com","Novi Sad","Srbija","student","Biology",r2));
    userDataRepository.save(new UserData(6L,"Jovan","Jovanovic","j@j.com","Novi Sad","Srbija","student","Computer Science",r3));



    List<UserData> fieldEditors = new ArrayList();
    fieldEditors.add(userDataRepository.findByUser(editorOfField));

    List<UserData> reviewers = new ArrayList();
    reviewers.add(userDataRepository.findByUser(r1));
    reviewers.add(userDataRepository.findByUser(r2));
    reviewers.add(userDataRepository.findByUser(r3));

    List<UserData> fieldEditors2 = new ArrayList();
    fieldEditors2.add(userDataRepository.findByUser(editorOfField));

    List<UserData> reviewers2 = new ArrayList();
    reviewers2.add(userDataRepository.findByUser(r1));
    reviewers2.add(userDataRepository.findByUser(r2));
    reviewers2.add(userDataRepository.findByUser(r3));


    Journal journal = new Journal(1L,"ABC123","Nature",true,"Computer Science, Biochemistry",new ArrayList<>(),userDataRepository.findByUser(editor),fieldEditors,reviewers,new HashSet<>());
    Journal journa2 = new Journal(2L,"ABC1234","IEEE",false,"Computer Science, Biochemistry",new ArrayList<>(),userDataRepository.findByUser(editor),fieldEditors,reviewers,new HashSet<>());

    journalRepository.save(journal);
    journalRepository.save(journa2);

  }

}
