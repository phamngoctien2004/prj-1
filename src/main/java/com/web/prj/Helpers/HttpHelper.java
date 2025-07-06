package com.web.prj.Helpers;

import com.web.prj.dtos.request.GoogleRequest;
import com.web.prj.dtos.response.GoogleResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpHelper {
    public static GoogleResponse post(GoogleRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GoogleRequest> httpEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token",
                httpEntity,
                GoogleResponse.class
        ).getBody();
    }

    public static GoogleResponse get(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://openidconnect.googleapis.com/v1/userinfo",
                HttpMethod.GET,
                httpEntity,
                GoogleResponse.class
        ).getBody();
    }
}
