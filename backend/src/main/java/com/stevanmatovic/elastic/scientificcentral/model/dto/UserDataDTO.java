package com.stevanmatovic.elastic.scientificcentral.model.dto;

import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {

  String name;

  String lastName;

  String email;

  String city;

  String country;

  String title;

  String researchFields;

  public UserDataDTO(UserData data) {
    this.name = data.getName();
    this.lastName = data.getLastName();
    this.city = data.getCity();
    this.email = data.getEmail();
    this.country = data.getCountry();
    this.title = data.getTitle();
    this.researchFields = data.getResearchFields();
  }
}
