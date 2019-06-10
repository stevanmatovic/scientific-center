package com.stevanmatovic.elastic.scientificcentral.service;

import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.repository.UserDataRepository;
import com.stevanmatovic.elastic.scientificcentral.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private UserDataRepository userDataRepository;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      UserDataRepository userDataRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userDataRepository = userDataRepository;
  }

  public User findByUsernameAndPassword(String username, String password) throws NotFoundException {
    User user = userRepository.findByUsername(username);

    if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
      throw new NotFoundException("Wrong username or password!");
    }
    return user;
  }

  public User findByUsername(String username) throws NotFoundException {
    User user = userRepository.findByUsername(username);
    return user;
  }

  public UserData getUserDataByUsername(String username){
    User user = userRepository.findByUsername(username);
    return userDataRepository.findByUser(user);
  }

  public UserData getUserData(User user) {
    return userDataRepository.findByUser(user);

  }

  public void save(User user){
    userRepository.save(user);
  }
}
