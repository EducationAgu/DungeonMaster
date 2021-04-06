package com.serverconnection.model;


import com.serverconnection.RestTemplateWithTimeOut;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class Querry {
    private String url;
    private HttpMethod method;
    private HttpEntity<String> entity;

    private RestTemplate restTemplate;

    public Querry(String url, HttpMethod method, HttpEntity<String> entity, RestTemplate restTemplate) {
        this.url = url;
        this.method = method;
        this.restTemplate = restTemplate;
        this.entity = entity;
    }

    public ResponseEntity<String> execute() {
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, method, entity, String.class);
        } catch (HttpStatusCodeException e) {
            response = new ResponseEntity<String>("", e.getStatusCode());
        } catch (ResourceAccessException e){
            response = new ResponseEntity<String>("", HttpStatus.SERVICE_UNAVAILABLE);
        }



        return response;
    }
}
