package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IDocumentStorageService {
    String storeFile(DriverDocuments driverDocuments, MultipartFile file) throws IOException;
}
