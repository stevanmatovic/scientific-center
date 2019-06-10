package com.stevanmatovic.elastic.scientificcentral.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Paper {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  String title;

  String paperAbstract;

  String keyWords;

  String researchField;

  String fileName;

  @ManyToOne
  @JoinColumn
  Journal journal;

  @OneToOne
  UserData author;

  public UserData getAuthor() {
    return this.author;
  }
}
