package com.stevanmatovic.elastic.scientificcentral.repository;

import com.stevanmatovic.elastic.scientificcentral.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String username);

}
