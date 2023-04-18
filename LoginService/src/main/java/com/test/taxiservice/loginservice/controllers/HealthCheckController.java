package com.test.taxiservice.loginservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.test.taxiservice.loginservice.constants.Constants.*;

@RestController
@RequestMapping(LOGINSERVICE_API_URL)
public class HealthCheckController {

    /**
     * GET
     * /driver/loginservice/healthcheck
     * @return Success message if the application is up.
     */
    @GetMapping(HEALTH_CHECK_URL)
    public String healthCheck() {
        return SUCCESS;
    }
}
