package com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching;

import lombok.Data;

@Data
public class SimpleQuery {

  private String field;
  private String value;
}
