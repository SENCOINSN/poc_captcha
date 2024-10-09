package com.example.service;

import com.example.dto.RecaptchaRequestDto;
import com.example.dto.RecaptchaResponse;
import com.example.exceptions.InvalidRequestException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.properties.CaptchaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class CaptchaService {
    private final CaptchaProperties captchaProperties;

    private final RestTemplate restTemplate;

    public RecaptchaResponse verify(String token) throws ResourceNotFoundException, InvalidRequestException {
        ResponseEntity<RecaptchaResponse> responseEntity=null;
        log.info("url : {}", captchaProperties.getUrl());
        RecaptchaRequestDto requestDto = new RecaptchaRequestDto(captchaProperties.getSecret(),
                token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("secret", requestDto.secret());
        map.add("response",token);

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(map,headers);
        try{
            responseEntity = restTemplate.exchange(
                    captchaProperties.getUrl(),
                    HttpMethod.POST,
                    entity,
                    RecaptchaResponse.class
            );

        }catch (HttpClientErrorException e){
            throw new ResourceNotFoundException(e.getMessage());
        }catch(Exception e){
            throw new InvalidRequestException(e.getMessage());
        }
        log.info("Response: {}", responseEntity.getBody().isSuccess());
        return responseEntity.getBody();
    }
}
