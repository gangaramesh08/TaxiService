package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DocumentType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

@Service
public interface IDriverDocService {

    /**
     * This method validates the document size and name.
     * @param driverId
     * @param documentType
     * @param file
     * @throws InvalidInputException
     */
    void validateDocuments(BigInteger driverId, String documentType, MultipartFile file) throws InvalidInputException;

    /**
     * This method uploads the documents to a storage location and saves the
     * details in the database.
     * @param driverId
     * @param documentType
     * @param file
     * @throws PersistenceException
     */
    void save(BigInteger driverId, DocumentType documentType, MultipartFile file) throws PersistenceException;
}
