package com.appress.quick_poll.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class QuickPollClientV3BasicAuth {

    public static final String QUICK_POLL_URI_v3 = "http://localhost:8080/v3/polls";

    private RestTemplate restTemplate = new RestTemplate();

    public void deletePoll(Long pollId) {
        HttpHeaders authenticationHeaders = getAuthenticationHeader("admin", "admin");
        restTemplate.exchange(QUICK_POLL_URI_v3 + "/{pollId}", HttpMethod.DELETE, new HttpEntity<Void>(authenticationHeaders), Void.class, pollId);
    }

    private HttpHeaders getAuthenticationHeader(String username, String password) {

        String credentials = username + ":" + password;
        byte[] base64CredentialData = Base64.getEncoder().encode(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + new String(base64CredentialData, StandardCharsets.UTF_8));

        return headers;
    }

    public static void main(String[] args) {

        QuickPollClientV3BasicAuth client = new QuickPollClientV3BasicAuth();
        client.deletePoll(1L);
        System.out.println("Poll with id 1 deleted");
    }
}
