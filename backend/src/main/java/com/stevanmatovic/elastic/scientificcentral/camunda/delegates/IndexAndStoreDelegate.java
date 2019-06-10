package com.stevanmatovic.elastic.scientificcentral.camunda.delegates;

import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnitService;
import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import com.stevanmatovic.elastic.scientificcentral.model.Paper;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.service.JournalService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class IndexAndStoreDelegate implements JavaDelegate {

  private static String DATA_DIR_PATH = "papers";

  @Autowired
  UserService userService;

  @Autowired
  JournalService journalService;

  @Autowired
  IndexUnitService indexUnitService;

  @Override
  public void execute(DelegateExecution execution) throws Exception {

    Paper paper = getPaper(execution);

    FileValue fileValue = execution.getVariableTyped("fileVariable");
    InputStream fileContent = fileValue.getValue();
    String fileName = fileValue.getFilename();

    paper.setFileName(fileName);

    byte[] bytes = StreamUtils.copyToByteArray(fileContent);
    Path path = Paths
        .get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + fileName);
    Files.write(path, bytes);
    String filePath = path.toString();

    indexUnitService.index(paper,filePath);
  }

  private Paper getPaper(DelegateExecution execution){

    String keywords = (String)execution.getVariable("keywords");
    String paperAbstract = (String)execution.getVariable("abstract");
    String title = (String) execution.getVariable("title");
    String researchField = (String) execution.getVariable("researchField");
    String author = (String) execution.getVariable("author");
    String journalName = (String) execution.getVariable("journalName");

    UserData authorData = userService.getUserDataByUsername(author);
    Journal journal = journalService.getByTitle(journalName);
    Paper paper = new Paper();

    paper.setAuthor(authorData);
    paper.setJournal(journal);
    paper.setKeyWords(keywords);
    paper.setPaperAbstract(paperAbstract);
    paper.setResearchField(researchField);
    paper.setTitle(title);

    return paper;
  }

  private File getResourceFilePath(String path) {
    URL url = this.getClass().getClassLoader().getResource(path);
    File file = null;
    try {
      file = new File(url.toURI());
    } catch (URISyntaxException e) {
      file = new File(url.getPath());
    }
    return file;
  }

}
