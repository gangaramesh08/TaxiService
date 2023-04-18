package com.test.taxiservice.drivertracking.controller;


import com.test.taxiservice.drivertracking.service.IDriverAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import static com.test.taxiservice.drivertracking.common.Constants.*;

@RestController
@RequestMapping(DRIVER_TRACKING_SERVICE_BASE_URL)
public class DriverAvailabilityController {

    @Autowired
    private IDriverAvailabilityService driverAvailabilityService;

    /**
     * GET
     * /driver/tracking/markavailable
     *
     * API to mark driver as available. A driver is marked available only after device shipment is complete.
     * @param driverId
     * @return
     */
    @GetMapping(DRIVER_MARK_AVAILABLE_URL)
    public ResponseEntity<String> markAsAvailable(@RequestParam BigInteger driverId) {
        driverAvailabilityService.markAsAvailable(driverId);
        return ResponseEntity.ok(SUCCESS);
    }
}
