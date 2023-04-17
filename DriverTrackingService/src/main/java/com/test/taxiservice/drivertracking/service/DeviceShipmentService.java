package com.test.taxiservice.drivertracking.service;


import com.test.taxiservice.drivertracking.model.DeviceStatusEnum;
import com.test.taxiservice.drivertracking.model.ExternalShipmentRequest;
import com.test.taxiservice.drivertracking.repository.DeviceStatusRepository;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.model.trackingservice.DeviceShipmentStatus;
import com.test.taxiservice.taxiservicecommon.service.IDeviceService;
import com.test.taxiservice.taxiservicecommon.service.IDriverStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class DeviceShipmentService implements IDeviceShipmentService {

    @Autowired
    IDriverStatusService driverStatusService;

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IDriverAvailabilityService driverAvailabilityService;

    @Autowired
    IExternalDeviceShipmentService externalDeviceShipmentService;


    @Override
    public boolean dispatchDevice(BigInteger driverId) {
        if(backgroundVerificationComplete(driverId)) {
            log.info("Background verification is complete for driverId : {}", driverId);
            externalDeviceShipmentService.initiateShipment(driverId);
            DeviceShipmentStatus deviceShipmentStatus = deviceStatusRepository.findByDriverId(driverId);
            if(deviceShipmentStatus == null ) {
                deviceShipmentStatus = new DeviceShipmentStatus();
                deviceShipmentStatus.setCreatedAt(new Date());
            }
            deviceShipmentStatus.setDriverId(driverId);
            deviceShipmentStatus.setModifiedAt(new Date());
            deviceShipmentStatus.setStatus(DeviceStatusEnum.INPROGRESS.getValue());
            deviceStatusRepository.save(deviceShipmentStatus);
            driverStatusService.updateStatus(driverId, DriverStatusEnum.DEVICE_SHIPMENT_INPROGRESS);

        }
        return false;
    }

    @Override
    public void validateExternalShipmentRequest(ExternalShipmentRequest externalShipmentRequest) {

    }

    @Override
    public void updateStatus(ExternalShipmentRequest externalShipmentRequest) {
        BigInteger shipmentId = externalShipmentRequest.getShipmentId();

        Optional<DeviceShipmentStatus> deviceShipmentStatusOptional = deviceStatusRepository
                .findById(externalShipmentRequest.getShipmentId());
        if(deviceShipmentStatusOptional.isPresent() && externalShipmentRequest.getStatus().equalsIgnoreCase(DeviceStatusEnum.COMPLETED.getValue())) {
            DeviceShipmentStatus deviceShipmentStatus = deviceShipmentStatusOptional.get();
            deviceShipmentStatus.setModifiedAt(new Date());
            deviceShipmentStatus.setStatus(DeviceStatusEnum.COMPLETED.getValue());
            log.info("Shipment of device is complete for driverId : {} and shipmentId : {}",
                    deviceShipmentStatus.getDriverId(),
                    shipmentId);
            deviceStatusRepository.save(deviceShipmentStatus);
            driverStatusService.updateStatus(deviceShipmentStatus.getDriverId(), DriverStatusEnum.DEVICE_SHIPMENT_COMPLETED);

        } else {
            log.error("Device shipment failed for shipmentId : {}",
                    shipmentId);
            driverStatusService.updateStatus(deviceShipmentStatusOptional.get().getDriverId(), DriverStatusEnum.DEVICE_SHIPMENT_FAILED);
        }

    }

    private boolean backgroundVerificationComplete(BigInteger driverId) {
        return driverStatusService.getStatus(driverId).equalsIgnoreCase(DriverStatusEnum.BACKGROUND_VERIFICATION_COMPLETED.getValue());
    }
}
