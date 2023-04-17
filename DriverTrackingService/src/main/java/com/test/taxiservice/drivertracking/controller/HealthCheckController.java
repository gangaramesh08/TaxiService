package com.test.taxiservice.drivertracking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static com.test.taxiservice.drivertracking.common.Constants.*;

@RestController
@RequestMapping(DRIVER_TRACKING_SERVICE_BASE_URL)
public class HealthCheckController {

    /**
     * GET
     * /driver/tracking/healthcheck
     * @return Success message if the application is up.
     */
    @GetMapping(HEALTH_CHECK_URL)
    public String healthCheck() {
        return SUCCESS;
    }
}
