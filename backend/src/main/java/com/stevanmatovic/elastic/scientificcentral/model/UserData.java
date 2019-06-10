package com.stevanmatovic.elastic.scientificcentral.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

  @Id
  Long id;

  String name;

  String lastName;

  String email;

  String city;

  String country;

  String title;

  String researchFields;

  @OneToOne
  @MapsId
  User user;
}
