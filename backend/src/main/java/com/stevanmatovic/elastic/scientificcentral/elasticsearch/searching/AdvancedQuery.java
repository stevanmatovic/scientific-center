package com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching;

import lombok.Data;

@Data
public class AdvancedQuery {

  private String field1;
  private String value1;
  private String field2;
  private String value2;
  private String operation;

}
