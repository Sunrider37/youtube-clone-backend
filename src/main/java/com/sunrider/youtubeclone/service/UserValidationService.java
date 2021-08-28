package com.sunrider.youtubeclone.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunrider.youtubeclone.dto.UserInfoDto;
import com.sunrider.youtubeclone.exception.YoutubeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    @Value("${auth0.userinfo")
    private String userInfoEndpoint;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2).build();

    public UserInfoDto validate(String authorizationHeader) {
        if(authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            HttpRequest request = HttpRequest.newBuilder()
                    .GET().uri(URI.create(userInfoEndpoint))
                    .setHeader("Authorization", String.format("Bearer %s", token))
                    .build();
            try{
                HttpResponse<String> response = httpClient.send(request,
                        HttpResponse.BodyHandlers.ofString());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(response.body(), UserInfoDto.class);
            } catch (Exception e) {
                throw new YoutubeException("Exception while validating user");
            }
            }else {
            throw new YoutubeException("Invalid Access Token");
        }
        }
    }

