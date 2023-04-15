package com.test.taxiservice.taxiservicecommon.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatus;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.repository.DriverStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

@Service
@Slf4j
public class DriverStatusService implements IDriverStatusService {

    @Autowired
    private DriverStatusRepository driverStatusRepository;

    @Override
    public void updateStatus(BigInteger driverId, DriverStatusEnum backgroundVerificationFailed) {
        DriverStatus driverStatus = new DriverStatus();
        driverStatus.setDriverId(driverId);
        driverStatus.setStatus(backgroundVerificationFailed.name());
        driverStatus.setModifiedAt(new Date());
        driverStatusRepository.save(driverStatus);
    }

    @Override
    public String getStatus(BigInteger driverId) {
        return driverStatusRepository.findDriverStatusById(driverId);
    }
}
