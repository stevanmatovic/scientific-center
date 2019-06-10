package com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequiredHighlight {

  private String fieldName;
  private String value;

}
