package com.centalabs.shift.api.opal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class AuthorizationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String opaUrl = "http://localhost:8181/v1/data/authz/allow";

    public boolean checkPermission(String user, String role, String action) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            String body = String.format("{\"input\": {\"user\": \"%s\", \"role\": \"%s\", \"action\": \"%s\"}}", user, role, action);
            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(opaUrl, HttpMethod.POST, requestEntity, String.class);
            return response.getBody().contains("\"result\":true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}