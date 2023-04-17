package com.test.taxiservice.drivertracking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Slf4j
public class ExternalDeviceShipmentService implements IExternalDeviceShipmentService{
    @Override
    public void initiateShipment(BigInteger driverId) {
        log.info("Shipment of tracking device initiated.");

    }
}
