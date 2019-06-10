package com.stevanmatovic.elastic.scientificcentral.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @Column(nullable = false, unique = true)
  String ISSN;

  String title;

  boolean openAccess;

  String researchFields;

  @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL)
  @JsonIgnore
  List<Paper> papers;

  @OneToOne
  UserData mainEditor;

  @ManyToMany
  List<UserData> editors;

  @ManyToMany
  List<UserData> reviewers;

  @ManyToMany
  Set<UserData> subscribers;
}
