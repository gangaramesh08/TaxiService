package com.test.taxiservice.documentservice.service;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.test.taxiservice.documentservice.common.Constants.DOCUMENTS;

@Service
public class LocalDocumentStorageService implements IDocumentStorageService{
    @Override
    public String storeFile(DriverDocuments driverDocuments, MultipartFile multipartFile) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(Objects.requireNonNull(multipartFile.getOriginalFilename()))) {

            Path source = Paths.get("");
            Path localFolder = Paths.get(
                    source.toAbsolutePath()
                            + File.separator
                            + DOCUMENTS
                            + File.separator
                            + driverDocuments.getDriverId()
                            + File.separator
                            + driverDocuments.getDocType()
                            + File.separator
                            + driverDocuments.getDocName());

            Files.createDirectories(localFolder);
            fos.write(multipartFile.getBytes());
            return localFolder.toAbsolutePath().toString();
        }

    }
}
