package com.test.taxiservice.documentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.test.taxiservice.documentservice.common.Constants.*;

@RestController
@RequestMapping(DOCUMENT_SERVICE_BASE_URL)
public class HealthCheckController {

    /**
     * GET
     * /driver/documentservice/healthcheck
     * @return Success message if the application is up.
     */
    @GetMapping(HEALTH_CHECK_URL)
    public String healthCheck() {
        return SUCCESS;
    }
}
