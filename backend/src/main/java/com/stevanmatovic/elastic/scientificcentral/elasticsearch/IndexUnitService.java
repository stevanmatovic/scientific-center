package com.stevanmatovic.elastic.scientificcentral.elasticsearch;

import com.stevanmatovic.elastic.scientificcentral.model.Paper;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class IndexUnitService {

  private IndexUnitRepository repository;

  @Autowired
  public IndexUnitService(IndexUnitRepository repository) {
    this.repository = repository;
  }

  public void index(Paper paper, String filePath) {

    File file = new File(filePath);
    PDFHandler pdfHandler = new PDFHandler();
    String content = pdfHandler.getText(file);
    IndexUnit indexUnit = paperToIndexUnit(paper);
    indexUnit.setContent(content);
    indexUnit.setFilename(paper.getFileName());
    this.repository.index(indexUnit);
  }

  public Iterable<IndexUnit> getAll() {
    return repository.findAll();
  }

  private IndexUnit paperToIndexUnit(Paper paper) {
    IndexUnit indexUnit = new IndexUnit();
    UserData author = paper.getAuthor();

    indexUnit.setAuthor(author.getName() + " " + author.getLastName());
    indexUnit.setResearchField(paper.getResearchField());
    indexUnit.setTitle(paper.getTitle());
    indexUnit.setKeywords(paper.getKeyWords());

    //todo set content ce morati kod rada sa fajlovima

    return indexUnit;
  }

  public List<IndexUnit> findByTitle(String title) {
    return this.repository.findByTitle(title);
  }
}
