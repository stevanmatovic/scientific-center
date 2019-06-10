package com.stevanmatovic.elastic.scientificcentral.model.dto;

import lombok.Data;

@Data
public class PaperDTO {

  Long id;

  String title;

  String paperAbstract;

  String keywords;

  String researchField;

  String fileName;

  Long journalId;

  Long authorId;

}
