package com.stevanmatovic.elastic.scientificcentral.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Review implements Serializable{

  private String comment;
  private String commentForEditors;
  private String acceptance;
  private String reviewer;

}
