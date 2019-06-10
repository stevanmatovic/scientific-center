package com.stevanmatovic.elastic.scientificcentral.controller;

import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.service.JournalService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalController {

  private JournalService journalService;
  private UserService userService;

  @Autowired
  public JournalController(JournalService journalService, UserService userService) {
    this.journalService = journalService;
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity getAll(){
    return new ResponseEntity<>(journalService.getAll(),HttpStatus.OK);
  }

  @PostMapping("/{journalName}/subscribe/{username}")
  public ResponseEntity subscribe(@PathVariable("journalName") String journalName,@PathVariable("username")String username) throws NotFoundException {
    User user = userService.findByUsername(username);
    UserData userData = userService.getUserData(user);
    journalService.subscribe(journalName,userData);

    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/{journalName}/reviewers")
  public ResponseEntity getReviewers(@PathVariable("journalName") String journalName) throws NotFoundException {
    Journal journal = journalService.getByTitle(journalName);
    return new ResponseEntity<>(journal.getReviewers(),HttpStatus.OK);
  }

}
