package com.stevanmatovic.elastic.scientificcentral.elasticsearch;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "digitallibrary", type = "paper", shards = 1, replicas = 0)
public class IndexUnit {

  @Field(type = FieldType.Text, store = true)
  private String journalName;

  @Field(type = FieldType.Text, store = true, analyzer = "serbian-analyzer")
  private String title;

  @Field(type = FieldType.Text, store = true)
  private String keywords;

  @Field(type = FieldType.Text, store = true, analyzer = "serbian-analyzer")
  private String author;

  @Field(type = FieldType.Text, store = true, analyzer = "serbian-analyzer")
  private String content;

  @Field(type = FieldType.Text, store = true)
  private String researchField;

  @Field(type = FieldType.Text, index = false)
  private String highlight;

  @Id
  @Field(type = FieldType.Text, index = true, store = true)
  private String filename;

}
