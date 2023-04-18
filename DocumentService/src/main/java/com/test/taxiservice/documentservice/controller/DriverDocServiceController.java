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
     * POST
     * /driver/documentservice/update :
     *
     * This API is used to upload documents of the driver.
     * Currently, support upload of PAN_CARD, AADHAR_CARD and DRIVING_LICENCE
     * @param file The document to be uploaded
     * @param driverId Id that uniquely identifies the driver
     * @param documentType Type of document that needs to be uploaded. Currently
     *                     only PAN_CARD, AADHAR_CARD and DRIVING_LICENCE are supported documentTypes.
     * @return Success message if documents are uploaded successfully( status code 200)
     * Or Bade request(400) if required data is not provided.
     * @throws InvalidInputException
     * @throws PersistenceException
     */
    @PostMapping(value = DOCUMENT_UPDATE_URL)
    public ResponseEntity<String> updateDocument(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam BigInteger driverId,
            @RequestParam String documentType) throws InvalidInputException, PersistenceException {
        driverDocService.validateDocuments(driverId, documentType,file);
        driverDocService.save(driverId, DocumentType.valueOf(documentType), file);
        return ResponseEntity.ok(SUCCESS);
    }
}
