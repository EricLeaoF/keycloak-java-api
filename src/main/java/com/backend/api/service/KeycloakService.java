package com.backend.api.service;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.api.dto.UserCreationDTO;
import com.backend.api.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KeycloakService {

  @Autowired
  private RestTemplate restTemplate;

  private final String keycloakUrl = "http://frontend/auth/admin/realms/firstrealm/users";
  String uri = UriComponentsBuilder.fromHttpUrl(keycloakUrl).toUriString();


  public List<UserDTO> listUsers(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<List<UserDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserDTO>>() {});
    return response.getBody();
  }

  public UserDTO getUserByUsername(String accessToken, String username) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<List<UserDTO>> response = restTemplate.exchange(uri + "?username=" + username, HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserDTO>>() {});
    List<UserDTO> users = response.getBody();
    if (users != null && !users.isEmpty()) {
      return users.get(0);
    } else {
      return null;
    }

  }

  public ResponseEntity<String> createUser(String accessToken, UserCreationDTO userCreationDTO) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

    String jsonBody = "{" +
      "\"firstName\": \"" + userCreationDTO.getFirstName() + "\"," +
      "\"lastName\": \"" + userCreationDTO.getLastName() + "\"," +
      "\"username\": \"" + userCreationDTO.getUsername() + "\"," +
      "\"email\": \"" + userCreationDTO.getEmail() + "\"," +
      "\"enabled\":  true," +
      "\"credentials\": [{" +
      "\"type\": \"password\"," +
      "\"value\": \"" + userCreationDTO.getPassword() + "\"," +
      "\"temporary\": false" +
      "}]" +
    "}";

    HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
      return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }

  public ResponseEntity<String> resetPassword(String accessToken, String id, String password) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

    String jsonBody = "{" +
      "\"temporary\": false," +
      "\"type\": \" password," +
      "\"value\": \"" + password + "\"" +
    "}";

    HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
      return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }

  }

  public ResponseEntity<String> updateUser(String accessToken, String id, Map<String, Object> userUpdateData) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = null;
    try {
      jsonBody = objectMapper.writeValueAsString(userUpdateData);
    } catch (Exception e) {
      e.printStackTrace();
    }

    HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(uri + "/" + id, HttpMethod.PUT, entity, String.class);
      return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }

  public ResponseEntity<String> deleteUser(String accessToken, String id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(uri + "/" + id, HttpMethod.DELETE, entity, String.class);
      return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }

  }
  
}
