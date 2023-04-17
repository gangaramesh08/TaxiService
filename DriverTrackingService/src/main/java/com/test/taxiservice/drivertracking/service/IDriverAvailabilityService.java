package com.test.taxiservice.drivertracking.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface IDriverAvailabilityService {
    void markAsAvailable(BigInteger driverId);
}
