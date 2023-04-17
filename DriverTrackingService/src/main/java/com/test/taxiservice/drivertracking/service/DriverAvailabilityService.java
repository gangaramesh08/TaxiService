package com.test.taxiservice.drivertracking.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.service.IDriverStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class DriverAvailabilityService implements IDriverAvailabilityService {

    @Autowired
    private IDriverStatusService driverStatusService;
    @Override
    public void markAsAvailable(BigInteger driverId) {
        driverStatusService.updateStatus(driverId, DriverStatusEnum.ACTIVE);
    }
}
