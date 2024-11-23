package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.dto.UserCreationDTO;
import com.backend.api.dto.UserDTO;
import com.backend.api.service.KeycloakService;

@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  private KeycloakService keycloakService;
  
  @GetMapping
  @CrossOrigin(origins = "http://frontend")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity <List<UserDTO>> list(@RequestHeader("Authorization") String authHeader) {
    String accessToken = authHeader.replace("Bearer ", "");
    List<UserDTO> users = keycloakService.listUsers(accessToken);
    
    if (users != null && !users.isEmpty()) {
      return ResponseEntity.ok(users);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> create(@RequestHeader("Authorization") String authHeader, @RequestBody UserCreationDTO userCreationDTO) {
    String accessToken = authHeader.replace("Bearer ", "");
    ResponseEntity<?> response = keycloakService.createUser(accessToken, userCreationDTO);

    if (response.getStatusCode() == HttpStatus.CREATED) {
      // TODO > Define the password for the user
    }

    return response;
  }

}
