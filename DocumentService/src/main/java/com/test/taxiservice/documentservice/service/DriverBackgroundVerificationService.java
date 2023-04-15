package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.documentservice.model.DriverBackgroundStatusEnum;
import com.test.taxiservice.documentservice.model.ExternalBGStatusRequest;
import com.test.taxiservice.documentservice.repository.DriverBackgroundStatusRepository;
import com.test.taxiservice.documentservice.repository.DriverDocumentsRepository;
import com.test.taxiservice.documentservice.utils.DriverDocumentsValidator;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverBackgroundVerification;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.service.IDriverStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

import static com.test.taxiservice.taxiservicecommon.common.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;

@Service
@Slf4j
public class DriverBackgroundVerificationService implements IDriverBackgroundVerificationService {

    @Autowired
    private DriverDocumentsRepository driverDocumentsRepository;

    @Autowired
    private DriverBackgroundStatusRepository driverBackgroundStatusRepository;

    @Autowired
    private ExternalBackgroundService externalBackgroundService;

    @Autowired
    private DriverDocumentsValidator driverDocumentsValidator;

    @Autowired
    private IDriverStatusService driverStatusService;

    @Override
    public boolean initiateCheck(BigInteger driverId) throws PersistenceException {
        try {
            if (allDocumentsUploaded(driverId)) {
                log.info("All mandatory documents are uploaded for the drvier : {}", driverId);
                DriverBackgroundVerification driverBackgroundVerification = new DriverBackgroundVerification();
                driverBackgroundVerification.setDriverId(driverId);
                driverBackgroundVerification.setCreatedAt(new Date());
                driverBackgroundVerification.setModifiedAt(driverBackgroundVerification.getCreatedAt());
                driverBackgroundVerification.setStatus(DriverBackgroundStatusEnum.INPROGRESS.getValue());
                driverBackgroundStatusRepository.save(driverBackgroundVerification);
                externalBackgroundService.initiateCheck(driverBackgroundVerification);
                log.info("Driver details for id : {} are sent for external background verification", driverId);
                driverStatusService.updateStatus(driverId, DriverStatusEnum.BACKGROUND_VERIFICATION_INPROGRESS);
                return true;
            }
        } catch (Exception exception) {
            log.error("Exception occurred while saving background check information in database for driverId : {}", driverId);
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
        return false;
    }

    @Override
    public void updateStatus(ExternalBGStatusRequest externalBGStatusRequest) {
        BigInteger verificationId = externalBGStatusRequest.getVerificationId();

        DriverBackgroundVerification driverBackgroundVerification = driverBackgroundStatusRepository
                .findById(externalBGStatusRequest.getVerificationId()).get();
        if(externalBGStatusRequest.getDocumentsList().isEmpty()) {
            log.info("Background verification is complete for driverId : {} and verificationId : {}",
                    driverBackgroundVerification.getDriverId(),
                    verificationId);
            driverBackgroundVerification.setModifiedAt(new Date());
            driverBackgroundVerification.setStatus(DriverBackgroundStatusEnum.COMPLETED.getValue());
            driverStatusService.updateStatus(driverBackgroundVerification.getDriverId(), DriverStatusEnum.BACKGROUND_VERIFICATION_COMPLETED);

        } else {
            log.info("Background verification failed for driverId :{}. verificationId : {}",
                    driverBackgroundVerification.getDriverId(), verificationId);
            driverStatusService.updateStatus(driverBackgroundVerification.getDriverId(), DriverStatusEnum.BACKGROUND_VERIFICATION_FAILED);
        }

    }

    @Override
    public void validateExternalBgRequest(ExternalBGStatusRequest externalBGStatusRequest) throws InvalidInputException {
        ErrorInfo validationError = driverDocumentsValidator.validateExternalBGRequest( externalBGStatusRequest);
        if( !validationError.getDetails().isEmpty() ) {
            validationError.setMessage(MessageConstants.INVALID_REQUEST);
            validationError.getDetails().forEach(errorDetails -> log.error(errorDetails.getMessage()));
            throw new InvalidInputException(validationError);
        }
    }

    private boolean allDocumentsUploaded(BigInteger driverId) {
        return DriverStatusEnum.DOCUMENT_UPLOAD_COMPLETE.getValue().equalsIgnoreCase(driverStatusService.getStatus(driverId));
    }
}
