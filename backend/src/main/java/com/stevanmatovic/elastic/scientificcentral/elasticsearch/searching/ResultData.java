package com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData {

  String title;
  String keywords;
  String author;
  String highlight;

}
