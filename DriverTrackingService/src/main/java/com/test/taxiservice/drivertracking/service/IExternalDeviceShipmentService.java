package com.test.taxiservice.drivertracking.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface IExternalDeviceShipmentService {
    void initiateShipment(BigInteger driverId);
}
