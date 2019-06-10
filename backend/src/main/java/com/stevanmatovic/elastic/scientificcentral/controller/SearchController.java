package com.stevanmatovic.elastic.scientificcentral.controller;

import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnit;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching.AdvancedQuery;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching.ResultRetriever;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching.SearchType;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching.SimpleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

  private ResultRetriever resultRetriever;

  @Autowired
  public SearchController(ResultRetriever resultRetriever) {
    this.resultRetriever = resultRetriever;
  }

  @PostMapping(value = "/term", consumes = "application/json")
  public ResponseEntity<List<IndexUnit>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {

    List<IndexUnit> results = resultRetriever.getResults(simpleQuery, SearchType.match);

    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  @PostMapping(value = "/boolean", consumes = "application/json")
  public ResponseEntity<List<IndexUnit>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
    List<IndexUnit> results = resultRetriever.getResults(advancedQuery);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  @PostMapping(value = "/phrase", consumes = "application/json")
  public ResponseEntity<List<IndexUnit>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
    List<IndexUnit> results = resultRetriever.getResults(simpleQuery, SearchType.phrase);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

}
