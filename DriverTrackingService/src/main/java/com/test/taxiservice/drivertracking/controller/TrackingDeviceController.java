package com.test.taxiservice.drivertracking.controller;

import com.test.taxiservice.drivertracking.common.HeaderInfo;
import com.test.taxiservice.drivertracking.model.ExternalShipmentRequest;
import com.test.taxiservice.drivertracking.service.IDeviceShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import static com.test.taxiservice.drivertracking.common.Constants.*;
import static com.test.taxiservice.taxiservicecommon.common.MessageConstants.BACKGROUND_VERIFICATION_NOT_COMPLETE;

@RestController
@RequestMapping(DEVICE_SHIPMENT)
public class TrackingDeviceController {

    @Autowired
    private IDeviceShipmentService deviceShipmentService;

    @Autowired
    private HeaderInfo headerInfo;

    /**
     * GET
     * /driver/device/shipment/initiate
     *
     * @param driverId ID of the driver to whom the device has to be shipped
     * @return Success(200)
     */
    @GetMapping(INITIATE_SHIPMENT)
    ResponseEntity<String> dispatchDevice(@RequestParam BigInteger driverId) {
        if(deviceShipmentService.dispatchDevice(driverId)) {
            return ResponseEntity.ok(SUCCESS);
        }

        return ResponseEntity.ok(BACKGROUND_VERIFICATION_NOT_COMPLETE);
    }

    /**
     * POST
     * /driver/device/shipment/update
     *
     * Api used for posting the status of device shipment.
     * @param externalShipmentRequest Request POJO to accept status of the device shipment
     * @return Success message as acknowledgement (status code 202)
     */
    @PostMapping(UPDATE_SHIPMENT_STATUS)
    ResponseEntity<String> updateShipmentStatus(@RequestBody ExternalShipmentRequest externalShipmentRequest) {
        deviceShipmentService.validateExternalShipmentRequest(externalShipmentRequest);
        new Thread(()->
            deviceShipmentService.updateStatus(externalShipmentRequest)
        ).start();

        return new ResponseEntity<>(SUCCESS,
                headerInfo.getResponseHeaders(),
                HttpStatus.ACCEPTED);
    }
}
