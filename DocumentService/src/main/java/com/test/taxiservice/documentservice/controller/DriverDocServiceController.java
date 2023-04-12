package com.test.taxiservice.documentservice.controller;

import com.test.taxiservice.documentservice.service.IDriverDocService;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

import static com.test.taxiservice.documentservice.common.Constants.*;

@RestController
@RequestMapping(DOCUMENT_SERVICE_BASE_URL)
public class DriverDocServiceController {

    @Autowired
    IDriverDocService driverDocService;

    /**
     * This API is used to upload documents of the driver.
     * Currently, support upload of PAN_CARD, AADHAR_CARD and DRIVING_LICENCE
     * @param file
     * @param driverId
     * @param documentType
     * @return
     * @throws InvalidInputException
     * @throws PersistenceException
     */
    @PostMapping(value = DOCUMENT_UPDATE_URL)
    public ResponseEntity<String> updateDocument(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam BigInteger driverId,
            @RequestParam String documentType) throws InvalidInputException, PersistenceException {
        driverDocService.validateDocuments(driverId,DocumentType.valueOf(documentType),file);
        driverDocService.save(driverId, DocumentType.valueOf(documentType), file);
        return ResponseEntity.ok(SUCCESS);
    }
}
