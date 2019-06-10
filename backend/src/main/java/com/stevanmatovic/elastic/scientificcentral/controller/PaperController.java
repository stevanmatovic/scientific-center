package com.stevanmatovic.elastic.scientificcentral.controller;

import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnit;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnitService;
import com.stevanmatovic.elastic.scientificcentral.model.Paper;
import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.model.dto.PaperDTO;
import com.stevanmatovic.elastic.scientificcentral.service.PaperService;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

  static final ModelMapper MAPPER = new ModelMapper();
  private static String DATA_DIR_PATH = "papers";

  private IndexUnitService indexUnitService;

  private PaperService paperService;

  private UserService userService;

  @Autowired
  public PaperController(IndexUnitService indexUnitService, PaperService paperService, UserService userService) {
    this.indexUnitService = indexUnitService;
    this.paperService = paperService;
    this.userService = userService;
  }

  @GetMapping(value = "/download/{fileName}")
  public ResponseEntity download(@PathVariable String fileName) throws IOException {

    Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + fileName);
    File result = new File(path.toUri());
    ResponseEntity respEntity = null;

    byte[] reportBytes = null;

    if (result.exists()) {
      InputStream inputStream = new FileInputStream(result);
      String type = result.toURL().openConnection().guessContentTypeFromName(fileName);

      byte[] out = StreamUtils.copyToByteArray(inputStream);
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
      responseHeaders.add("Content-Type", type);

      respEntity = new ResponseEntity(out, responseHeaders, HttpStatus.OK);
    } else {
      respEntity = new ResponseEntity("File Not Found", HttpStatus.OK);
    }
    return respEntity;
  }



  @GetMapping(value = "/all")
  public ResponseEntity<List<IndexUnit>> getAll() {
    List<IndexUnit> list = new ArrayList<>();
    Iterable<IndexUnit> ret = indexUnitService.getAll();
    ret.forEach(list::add);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping(value = "/title/{title}")
  public List<IndexUnit> searchTitle(@PathVariable final String title) {
    return indexUnitService.findByTitle(title);
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
