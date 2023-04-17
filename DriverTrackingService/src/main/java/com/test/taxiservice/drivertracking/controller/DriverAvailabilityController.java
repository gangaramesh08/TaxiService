package com.test.taxiservice.drivertracking.controller;


import com.test.taxiservice.drivertracking.service.IDriverAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import static com.test.taxiservice.drivertracking.common.Constants.SUCCESS;

@RestController
@RequestMapping
public class DriverAvailabilityController {

    @Autowired
    private IDriverAvailabilityService driverAvailabilityService;

    @GetMapping
    public ResponseEntity<String> markAsAvailable(@RequestParam BigInteger driverId) {
        driverAvailabilityService.markAsAvailable(driverId);
        return ResponseEntity.ok(SUCCESS);
    }
}
