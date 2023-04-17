package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.documentservice.repository.DriverDocumentsRepository;
import com.test.taxiservice.documentservice.utils.DriverDocumentsValidator;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DocumentType;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.service.IDriverStatusService;
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
    private IDriverStatusService driverStatusService;

    @Autowired
    private DriverDocumentsValidator validator;


    @Override
    public void validateDocuments(BigInteger driverId, String documentType, MultipartFile multipartFile) throws InvalidInputException {
        ErrorInfo validationError = validator.validateDriverDetails( multipartFile, documentType);
        if( !validationError.getDetails().isEmpty() ) {
            log.error("Invalid details provided in the request for driverId : {}, docType : {}", driverId, documentType);
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
            BigInteger documentId = documentAlreadyExists(driverId, documentType.getValue());
            if(documentId!=null) {
                driverDocuments.setDocumentId(documentId);
                log.info("Updated the document for driverId : {}, docType : {}",driverId, documentType);
            }

            driverDocumentsRepository.save(driverDocuments);
            long documentsUploadedCount = documentsUploaded(driverId);
            if(documentsUploadedCount == 1) {
                driverStatusService.updateStatus(driverId, DriverStatusEnum.DOCUMENT_UPLOAD_INPROGRESS);

            }
            else if(documentsUploadedCount== 3) {
                driverStatusService.updateStatus(driverId, DriverStatusEnum.DOCUMENT_UPLOAD_COMPLETE);
            }


        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database for driverId :{}", driverId);
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }

    }

    private BigInteger documentAlreadyExists(BigInteger driverId, String documentType) {
        return driverDocumentsRepository.findByDocumentType(driverId, documentType);
    }

    private long documentsUploaded(BigInteger driverId) {
        return driverDocumentsRepository.getDocumentCountByDriverId(driverId);
    }
}
