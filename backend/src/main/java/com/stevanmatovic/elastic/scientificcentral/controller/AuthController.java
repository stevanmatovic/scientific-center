package com.stevanmatovic.elastic.scientificcentral.controller;

import com.stevanmatovic.elastic.scientificcentral.model.User;
import com.stevanmatovic.elastic.scientificcentral.model.UserData;
import com.stevanmatovic.elastic.scientificcentral.model.dto.UserDTO;
import com.stevanmatovic.elastic.scientificcentral.model.dto.UserDataDTO;
import com.stevanmatovic.elastic.scientificcentral.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> login(@RequestBody UserDTO userDto) throws NotFoundException {
    User user = userService.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());

    Collection<? extends GrantedAuthority> authorities;
    try {
      authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
    } catch (Exception e) {
      authorities = null;
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return ResponseEntity.ok(user);
  }

  @DeleteMapping
  public ResponseEntity logout() {
    SecurityContextHolder.clearContext();
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping(value = "/current")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity currentUser(@AuthenticationPrincipal User user) {
    UserData data = userService.getUserData(user);
    UserDataDTO dto = new UserDataDTO(data);
    return ResponseEntity.ok(dto);
  }

}
