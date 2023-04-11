package com.test.taxiservice.apigw.service;

import com.test.taxiservice.apigw.repository.redis.DriverAccessTokenRepository;
import com.test.taxiservice.taxiservicecommon.model.DriverAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class APIAuthenticatorService
{
    @Autowired
    DriverAccessTokenRepository driverAccessTokenRepository;
    public  boolean authenticateRequest(HttpServletRequest request, String mobileNumber){
        String bearerToken = request.getHeader("Authorization");
        if(Objects.nonNull(bearerToken)) {
            Optional<DriverAccessToken> byId = driverAccessTokenRepository.findById(mobileNumber);
            Optional<DriverAccessToken> token = driverAccessTokenRepository.findById(mobileNumber);

            if (byId.isPresent() && token.isPresent()) {
                log.info("bearerToken=" + bearerToken);
                return bearerToken.equals(token.get().getToken());
            }
        }
        return false;
    }
}