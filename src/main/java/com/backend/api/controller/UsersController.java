package com.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.service.KeycloakService;

@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  private KeycloakService keycloakService;
  
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public String list(@RequestHeader("Authorization") String authHeader) {
    String accessToken = authHeader.replace("Bearer ", "");
    return keycloakService.listUsers(accessToken);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public String create() {
    return "Cadastrando usuários";
  }

}
