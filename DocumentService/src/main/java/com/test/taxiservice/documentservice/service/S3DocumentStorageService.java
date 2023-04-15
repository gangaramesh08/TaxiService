package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3DocumentStorageService implements IDocumentStorageService{
    @Override
    public String storeFile(DriverDocuments driverDocuments, MultipartFile file) {
        return null;
    }
}
