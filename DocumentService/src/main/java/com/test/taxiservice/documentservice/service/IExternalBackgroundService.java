package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverBackgroundVerification;
import org.springframework.stereotype.Service;

@Service
public interface IExternalBackgroundService {

    void initiateCheck(DriverBackgroundVerification driverBackgroundVerification);
}
