package org.lukaszse.carRental.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.lukaszse.carRental.model.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Data
@RequiredArgsConstructor
public class ReCaptchaValidationService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.key.secret}")
    private String RECAPTCHA_SECRET;

    @Value("${google.recaptcha.key.site}")
    private String RECAPTCHA_SITE;

    private final RestTemplate restTemplate;


    public boolean validateCaptcha(String captchaResponse){
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", RECAPTCHA_SECRET);
        requestMap.add("response", captchaResponse);
        ReCaptchaResponse apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, ReCaptchaResponse.class);
        if(apiResponse == null){
            return false;
        }
        return Boolean.TRUE.equals(apiResponse.isSuccess());
    }
}