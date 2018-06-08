package org.mvpigs.MedicalDB.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiData {

  private String server = "https://api.fda.gov/drug/event.json?search=";
  private RestTemplate rest;
  private HttpHeaders headers;
  private HttpStatus status;
  private static final Logger LOG = LoggerFactory.getLogger(ApiData.class);
  public ApiData() {
    this.rest = new RestTemplate();
    this.headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Accept", "*/*");
  }

  public String get(String uri) {
    HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
    ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
    LOG.info(responseEntity.toString());
    LOG.info("desde apidata-------------------------------------------------------------------------");
    this.setStatus(responseEntity.getStatusCode());
    return responseEntity.toString();
  }

  private void setStatus(HttpStatus statusCode) {
	this.status=statusCode;
	
}

public String post(String uri, String json) {   
    HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
    ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
    this.setStatus(responseEntity.getStatusCode());
    return responseEntity.getBody();
  }






}