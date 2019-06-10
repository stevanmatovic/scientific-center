package com.stevanmatovic.elastic.scientificcentral;

import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnit;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnitRepository;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.PDFHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class PaperLoader {

  @Autowired
  IndexUnitRepository repository;

  //@PostConstruct
  public void load() throws Exception {

    File file = getResourceFilePath("papers");
    File files[] = file.listFiles();
    PDFHandler handler = new PDFHandler();
    for (File newFile : files) {

      String fileName = newFile.getName();

      IndexUnit indexUnit = handler.getIndexUnit(newFile);
      indexUnit.setFilename(fileName);
      indexUnit.setJournalName("Nature");
      add(indexUnit);
    }
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

  public boolean add(IndexUnit unit) {
    unit = repository.index(unit);
    if (unit != null)
      return true;
    else
      return false;
  }

}
