package com.test.taxiservice.apigw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.test.taxiservice.apigw.common.Constants.HEALTH_CHECK;

@RestController
public class ApiGwController {

    @GetMapping(HEALTH_CHECK)
    public String healthCheck() {
        return "Health check successful";
    }
}