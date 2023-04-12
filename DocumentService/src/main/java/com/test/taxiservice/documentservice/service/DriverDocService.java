package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.documentservice.repository.DriverDocumentsRepository;
import com.test.taxiservice.documentservice.utils.DriverDocumentsValidator;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DocumentType;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Date;

import static com.test.taxiservice.taxiservicecommon.common.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;

@Service
@Slf4j
public class DriverDocService implements IDriverDocService {

    @Value("${document.storage.type}")
    private String documentStorageType;

    @Autowired
    private DocumentStorageFactory documentStorageFactory;

    @Autowired
    private DriverDocumentsRepository driverDocumentsRepository;

    @Autowired
    private DriverDocumentsValidator validator;


    @Override
    public void validateDocuments(BigInteger driverId, DocumentType documentType, MultipartFile multipartFile) throws InvalidInputException {
        ErrorInfo validationError = validator.validateDriverDetails( multipartFile);
        if( !validationError.getDetails().isEmpty() ) {
            validationError.setMessage(MessageConstants.INVALID_DRIVER_INFO);
            validationError.getDetails().forEach(errorDetails -> log.error(errorDetails.getMessage()));
            throw new InvalidInputException(validationError);
        }
    }

    @Override
    public void save(BigInteger driverId, DocumentType documentType, MultipartFile file) throws PersistenceException {
        try {
            IDocumentStorageService documentStorageService  = documentStorageFactory
                    .getStorageStrategy(documentStorageType);
            DriverDocuments driverDocuments = new DriverDocuments();
            driverDocuments.setDriverId(driverId);
            driverDocuments.setDocType(documentType.getValue());
            driverDocuments.setDocName(file.getOriginalFilename());
            driverDocuments.setCreatedAt(new Date());
            driverDocuments.setModifiedAt(driverDocuments.getCreatedAt());
            driverDocuments.setStorageLink(documentStorageService.storeFile(driverDocuments, file));
            log.info("Successfully stored the file for driverId : {}  in location : {}",
                    driverDocuments.getDriverId(), driverDocuments.getStorageLink());
            driverDocumentsRepository.save(driverDocuments);

        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database");
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }

    }
}
