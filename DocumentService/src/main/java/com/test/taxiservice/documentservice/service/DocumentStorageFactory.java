package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.documentservice.model.DocumentStorageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DocumentStorageFactory {

    @Autowired
    private LocalDocumentStorageService localDocumentStorageService;

    @Autowired
    private AWSDocumentStorageService awsDocumentStorageService;

    public IDocumentStorageService getStorageStrategy(String documentStorageType) {
        DocumentStorageType type = DocumentStorageType.valueOf(documentStorageType);

        switch (type) {
            case LOCAL: return localDocumentStorageService;
            case AWS : return awsDocumentStorageService;
            default: return null;
        }
    }
}
