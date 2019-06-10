package com.stevanmatovic.elastic.scientificcentral.controller;

import com.stevanmatovic.elastic.scientificcentral.model.Review;
import com.stevanmatovic.elastic.scientificcentral.model.dto.PaperDTO;
import com.stevanmatovic.elastic.scientificcentral.service.JournalService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/camunda")
public class CamundaController {

  static final ModelMapper MAPPER = new ModelMapper();

  @Autowired
  RuntimeService runtimeService;

  @Autowired
  TaskService taskService;

  @Autowired
  JournalService journalService;

  @PostMapping(value = "/hello")
  public ResponseEntity hello() {
    return new ResponseEntity("Hello World",HttpStatus.OK);
  }

  @PostMapping(value = "/paperData/{processId}")
  public ResponseEntity getData(@RequestBody PaperDTO dto, @PathVariable String processId) {

    runtimeService.setVariable(processId,"keywords",dto.getKeywords());
    runtimeService.setVariable(processId,"abstract",dto.getPaperAbstract());
    runtimeService.setVariable(processId,"researchField",dto.getResearchField());
    runtimeService.setVariable(processId,"title",dto.getTitle());
    return new ResponseEntity(HttpStatus.OK);
  }

  @PostMapping(value = "/paperFile/{processId}")
  public ResponseEntity getFile(@PathVariable String processId, @RequestParam("file") MultipartFile file) {

    File convFile = new File(file.getOriginalFilename());
    try {
      convFile.createNewFile();
      FileOutputStream fos = new FileOutputStream(convFile);
      fos.write(file.getBytes());
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    FileValue fileValue = Variables.fileValue(convFile);
    runtimeService.setVariable(processId, "fileVariable", fileValue);
    Task task = taskService.createTaskQuery().active().processInstanceId(processId).singleResult();
    taskService.complete(task.getId());
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @GetMapping(value = "/{processId}/reviewers")
  public ResponseEntity getPotentialReviewers(@PathVariable String processId) {
    String journalName = (String)runtimeService.getVariable(processId,"journalName");
    return new ResponseEntity<>(journalService.getByTitle(journalName).getReviewers(),HttpStatus.OK);
  }

  @PostMapping(value = "/{taskId}/reviewers")
  public ResponseEntity chooseReviewers(@PathVariable String taskId,@RequestBody List<String> reviewers) {

    Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
    runtimeService.setVariable(task.getProcessInstanceId(),"reviewers",reviewers);
    runtimeService.setVariable(task.getProcessInstanceId(),"previousReviewers",reviewers);
    runtimeService.setVariable(task.getProcessInstanceId(),"reviews",new ArrayList<>());
    taskService.complete(task.getId());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/{taskId}/newReviewers")
  public ResponseEntity chooseNewReviewers(@PathVariable String taskId,@RequestBody List<String> reviewers) {

    Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
    runtimeService.setVariable(task.getProcessInstanceId(),"reviewers",reviewers);
    List<String> previousReviewers = (List<String>)runtimeService.getVariable(task.getProcessInstanceId(),"previousReviewers");
    previousReviewers.addAll(reviewers);
    runtimeService.setVariable(task.getProcessInstanceId(),"previousReviewers",previousReviewers);
    taskService.complete(task.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/{taskId}/review")
  public ResponseEntity submitReview(@PathVariable String taskId,@RequestBody Review review) {

    Task task = taskService.createTaskQuery().active().taskId(taskId).singleResult();
    String processInstanceId = task.getProcessInstanceId();
    List<Review> currentReviews = (List<Review>) runtimeService.getVariable(processInstanceId,"reviews");
    currentReviews.add(review);
    runtimeService.setVariable(task.getProcessInstanceId(),"reviews",currentReviews);
    taskService.complete(task.getId());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
