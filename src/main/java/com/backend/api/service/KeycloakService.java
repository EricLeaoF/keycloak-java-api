package com.backend.api.service;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KeycloakService {

  @Autowired
  private RestTemplate restTemplate;

  private final String keycloakUrl = "http://frontend/auth/admin/realms/firstrealm/users";

  public String listUsers(String accessToken) {
    String uri = UriComponentsBuilder.fromHttpUrl(keycloakUrl).toUriString();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    return response.getBody();
  }
  
}
