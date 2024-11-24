package com.backend.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ResponseEntity <List<UserDTO>> listUsers(@RequestHeader("Authorization") String authHeader) {
    String accessToken = authHeader.replace("Bearer ", "");
    List<UserDTO> users = keycloakService.listUsers(accessToken);
    
    if (users != null && !users.isEmpty()) {
      return ResponseEntity.ok(users);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @PostMapping
  @CrossOrigin(origins = "http://frontend")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> createUser(@RequestHeader("Authorization") String authHeader, @Validated @RequestBody UserCreationDTO userCreationDTO) {
    String accessToken = authHeader.replace("Bearer ", "");
    ResponseEntity<?> response = keycloakService.createUser(accessToken, userCreationDTO);

    return response;
  }

  @DeleteMapping("/{id}")
  @CrossOrigin(origins = "http://frontend")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable(value = "id") String id) {
    String accessToken = authHeader.replace("Bearer ", "");
    ResponseEntity<?> response = keycloakService.deleteUser(accessToken, id);

    return response;
  }

  @PutMapping("/{id}")  
  @CrossOrigin(origins = "http://frontend")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authHeader, @PathVariable(value = "id") String id, @RequestBody Map<String, Object> userUpdateData) {
    String accessToken = authHeader.replace("Bearer ", "");
    ResponseEntity<?> response = keycloakService.updateUser(accessToken, id, userUpdateData);

    return response;
  }
}


