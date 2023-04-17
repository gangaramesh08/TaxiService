package com.test.taxiservice.drivertracking.service;

import com.test.taxiservice.drivertracking.model.ExternalShipmentRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface IDeviceShipmentService {
    boolean dispatchDevice(BigInteger driverId);

    void validateExternalShipmentRequest(ExternalShipmentRequest externalShipmentRequest);

    void updateStatus(ExternalShipmentRequest externalShipmentRequest);

}
