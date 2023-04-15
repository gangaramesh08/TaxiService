package com.test.taxiservice.taxiservicecommon.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface IDriverStatusService {
    void updateStatus(BigInteger driverId, DriverStatusEnum backgroundVerificationFailed);

    String getStatus(BigInteger driverId);
}
