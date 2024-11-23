package com.backend.api.service;

import java.util.List;

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

@Service
public class KeycloakService {

  @Autowired
  private RestTemplate restTemplate;

  private final String keycloakUrl = "http://frontend/auth/admin/realms/firstrealm/users";

  public List<UserDTO> listUsers(String accessToken) {
    String uri = UriComponentsBuilder.fromHttpUrl(keycloakUrl).toUriString();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<List<UserDTO>> response = restTemplate.exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<List<UserDTO>>() {});
    return response.getBody();
  }

  public ResponseEntity<String> createUser(String accessToken, UserCreationDTO userCreationDTO) {
    String uri = UriComponentsBuilder.fromHttpUrl(keycloakUrl).toUriString();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

    String jsonBody = "{" +
      "\"firstName\": \"" + userCreationDTO.getFirstName() + "\"," +
      "\"lastName\": \"" + userCreationDTO.getLastName() + "\"," +
      "\"username\": \"" + userCreationDTO.getUsername() + "\"," +
      "\"email\": \"" + userCreationDTO.getEmail() + "\"" +
    "}";

    HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

    try {
      ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
      return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
  }
  
}
