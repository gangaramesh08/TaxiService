package com.test.taxiservice.documentservice.controller;

import com.test.taxiservice.documentservice.common.HeaderInfo;
import com.test.taxiservice.documentservice.model.ExternalBGStatusRequest;
import com.test.taxiservice.documentservice.service.IDriverBackgroundVerificationService;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import static com.test.taxiservice.documentservice.common.Constants.*;
import static com.test.taxiservice.taxiservicecommon.common.MessageConstants.DOCUMENT_UPLOAD_NOT_COMPLETE;

@RestController
@RequestMapping(DOCUMENT_SERVICE_BASE_URL)
@Slf4j
public class DriverBackgroundController {

    @Autowired
    private IDriverBackgroundVerificationService driverBackgroundStatusService;

    @Autowired
    private HeaderInfo headerInfo;

    /**
     * GET
     * /driver/documentservice/backgroundcheck/initiate:
     * Initates background Verification process for the driver once all the
     * necessary documents are uploaded.
     *
     * @param driverId Id that uniquely identifies the driver
     * @return Success message (status code 200) if process is initiated successfully.
     * Or Responds with a message that indicates that document upload is not completed.
     * @throws PersistenceException
     */
    @GetMapping(INITIATE_DRIVER_BACKGROUND_CHECK_URL)
    public ResponseEntity<String> initiateBackgroundCheck(@RequestParam BigInteger driverId) throws PersistenceException {
        if(driverBackgroundStatusService.initiateCheck(driverId)){
            return ResponseEntity.ok(SUCCESS);
        }
        return ResponseEntity.ok(DOCUMENT_UPLOAD_NOT_COMPLETE);
    }

    /**
     * POST
     * /driver/documentservice/backgroundcheck/update:
     *
     * @param externalBGStatusRequest Request POJO for updating driver background verification status
     * @return Success message once the request is accepted(status code 202)
     * Or Bad Request(status code 400) if the data sent is incorrect or invalid.
     * @throws InvalidInputException
     */
    @PostMapping(UPDATE_DRIVER_BACKGROUND_CHECK_STATUS_URL)
    public ResponseEntity<String> updateBackGroundCheckStatus(@RequestBody ExternalBGStatusRequest externalBGStatusRequest) throws InvalidInputException {
        driverBackgroundStatusService.validateExternalBgRequest(externalBGStatusRequest);
        CompletableFuture.runAsync(()-> driverBackgroundStatusService.updateStatus(externalBGStatusRequest));
        log.debug(ACCEPTED_RESPONSE);

        return new ResponseEntity<>(SUCCESS,
                headerInfo.getResponseHeaders(),
                HttpStatus.ACCEPTED);
    }
}
