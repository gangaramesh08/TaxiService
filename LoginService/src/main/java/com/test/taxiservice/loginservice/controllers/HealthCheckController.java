package com.test.taxiservice.loginservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.test.taxiservice.loginservice.constants.Constants.LOGINSERVICE_API_URL;
import static com.test.taxiservice.loginservice.constants.Constants.LOGIN_HEALTH_CHECK_URL;

@RestController
@RequestMapping(LOGINSERVICE_API_URL)
public class HealthCheckController {

    @GetMapping(LOGIN_HEALTH_CHECK_URL)
    public String healthCheck() {
        return "Health check successful";
    }
}