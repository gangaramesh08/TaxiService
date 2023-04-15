package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.documentservice.model.ExternalBGStatusRequest;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Handles all the background verification processes for driver module
 */
@Service
public interface IDriverBackgroundVerificationService {
     boolean initiateCheck(BigInteger driverId) throws PersistenceException;

     void updateStatus(ExternalBGStatusRequest externalBGStatusRequest);

     void validateExternalBgRequest(ExternalBGStatusRequest externalBGStatusRequest) throws InvalidInputException;
}
