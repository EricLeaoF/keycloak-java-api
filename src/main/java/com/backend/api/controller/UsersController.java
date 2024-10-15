package com.backend.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
  
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public String list() {
    return "Listando usuários";
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public String create() {
    return "Cadastrando usuários";
  }

}
