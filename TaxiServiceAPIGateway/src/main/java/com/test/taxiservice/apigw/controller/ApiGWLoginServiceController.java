package com.test.taxiservice.apigw.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.taxiservice.apigw.common.Constants;
import com.test.taxiservice.apigw.service.APIAuthenticatorService;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

import static com.test.taxiservice.apigw.common.Constants.ApiGWUrl.*;
import static com.test.taxiservice.apigw.common.Constants.DRIVER_MOBILE_NUMBER;
import static com.test.taxiservice.apigw.common.Constants.DRIVER_OTP;

@RestController
@RequestMapping(Constants.ApiGWUrl.APIGW)
public class ApiGWLoginServiceController
{
    @Autowired
    APIAuthenticatorService apiAuthenticatorService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${loginservice.host}")
    private String loginServiceHost;

    @Value("${loginservice.login.url}")
    private String loginServiceLoginUrl;

    @PostMapping(LOGINSERVICE_SIGNUP_URL)
    public ResponseEntity<Integer>  driverSignUp(@RequestBody SignUpInfo signUpInfo) throws URISyntaxException {
        JsonNode requestPayload = objectMapper.convertValue(signUpInfo, JsonNode.class);
        URI serviceUri = new URI(loginServiceLoginUrl + LOGINSERVICE_SIGNUP_URL);
        RequestEntity<JsonNode> requestEntity = RequestEntity.post(serviceUri ).body(requestPayload);
        return restTemplate.exchange(requestEntity, Integer.class);
    }

    @PostMapping(LOGINSERVICE_LOGIN_URL)
    public ResponseEntity<String>  driverLogin(@RequestBody SignUpInfo signUpInfo) throws URISyntaxException {
            JsonNode requestPayload = objectMapper.convertValue(signUpInfo, JsonNode.class);
            URI serviceUri = new URI(loginServiceLoginUrl + LOGINSERVICE_LOGIN_URL);
            RequestEntity<JsonNode> requestEntity = RequestEntity.post(serviceUri ).body(requestPayload);
            return restTemplate.exchange(requestEntity, String.class);

    }

    @GetMapping(LOGINSERVICE_VALIDATE_OTP_URL)
    public ResponseEntity<Boolean> validateDriverOTP(@RequestParam String mobileNumber, @RequestParam Integer otp){
        URI serviceUri = UriComponentsBuilder.fromHttpUrl(loginServiceLoginUrl + LOGINSERVICE_VALIDATE_OTP_URL)
                .queryParam(DRIVER_MOBILE_NUMBER, mobileNumber)
                .queryParam(DRIVER_OTP, otp)
                .build().toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(serviceUri).build();
        return restTemplate.exchange(requestEntity, Boolean.class);

    }

    @GetMapping(LOGINSERVICE_REGENERATE_OTP_URL)
    public ResponseEntity<Integer> regenerateDriverOTP(@RequestParam String mobileNumber){
        URI serviceUri = UriComponentsBuilder.fromHttpUrl(loginServiceLoginUrl + LOGINSERVICE_REGENERATE_OTP_URL)
                .queryParam(DRIVER_MOBILE_NUMBER, mobileNumber)
                .build().toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(serviceUri).build();
        return restTemplate.exchange(requestEntity, Integer.class);

    }

    @PostMapping(LOGINSERVICE_PROFILE_UPDATE_URL)
    public ResponseEntity<String>  driverProfileUpdate(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody DriverProfileUpdate driverProfileUpdate) throws URISyntaxException {

        boolean isAuthenticated = apiAuthenticatorService.authenticateRequest(request, driverProfileUpdate.getMobileNumber());
        if(isAuthenticated){

            JsonNode requestPayload = objectMapper.convertValue(driverProfileUpdate, JsonNode.class);
            URI serviceUri = new URI(loginServiceLoginUrl + LOGINSERVICE_PROFILE_UPDATE_URL);
            RequestEntity<JsonNode> requestEntity = RequestEntity.post(serviceUri ).body(requestPayload);
            return restTemplate.exchange(requestEntity, String.class);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

    }
}
