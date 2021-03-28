package com.serverconnection;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;

public class Person {
    public static void connect() {
        String url =  "http://192.168.0.103:8080";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = "";
        //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders headers;
            String notEncoded = "new user:pass";
            String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Basic " + encodedAuth);

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            result = e.getMessage();
            return;
        }
        System.out.println(response.getBody());
    }

}
