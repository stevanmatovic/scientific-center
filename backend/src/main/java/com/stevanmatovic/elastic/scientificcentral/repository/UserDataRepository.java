package com.stevanmatovic.elastic.scientificcentral.repository;

import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

  UserData findByUser(User user);
}
