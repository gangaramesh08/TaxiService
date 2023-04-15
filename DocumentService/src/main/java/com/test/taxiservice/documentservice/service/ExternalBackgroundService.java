package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverBackgroundVerification;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.service.DriverStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExternalBackgroundService implements IExternalBackgroundService{

    @Autowired
    private DriverStatusService driverStatusService;

    @Override
    public void initiateCheck(DriverBackgroundVerification driverBackgroundVerification) {
        log.info("This is an externalService call");
        driverStatusService.updateStatus(driverBackgroundVerification.getDriverId(), DriverStatusEnum.BACKGROUND_VERIFICATION_INPROGRESS);
    }
}
