package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class HttpRequestClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestClient.class);

    private final DiscoveryClient discoveryClient;

    public HttpRequestClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public boolean sendRequest(String username, String password){
        //gestire caso del subscriber dump
        String serviceUri = this.getZuulInstance();
        if(serviceUri == null)
            return false;
        else{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", username);
            map.add("password", password);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, httpHeaders);
            ResponseEntity<String> response;
            try{
                response = restTemplate.postForEntity(serviceUri, entity, String.class);
            }catch (RestClientException rce){
                logger.error(rce.getMessage());
                return false;
            }
            return response.getStatusCodeValue() == 200;
        }
    }

    private String getZuulInstance(){
        List<ServiceInstance> list = discoveryClient.getInstances("zuul");
        if(list.size() == 0){
            return null;
        }
        return list.get(0).getUri().toString()+"/auth/sniffer";
    }
}
