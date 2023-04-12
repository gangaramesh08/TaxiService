package com.test.taxiservice.documentservice.utils;

import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorDetails;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.ErrorResponse;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverDocuments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.test.taxiservice.documentservice.common.Constants.DOCUMENT_NAME;
import static com.test.taxiservice.documentservice.common.Constants.DOCUMENT_NAME_REGEX;

@Component
@Slf4j
public class DriverDocumentsValidator {

    @Value("${document.max-file-size}")
    private long maxFileUploadSize;
    public ErrorInfo validateDriverDetails( MultipartFile file) {
        ErrorResponse errorResponse = new ErrorResponse();
        ErrorInfo error = new ErrorInfo();
        error.setDetails(new ArrayList<>());
        errorResponse.setError(error);
        if(Objects.isNull(file)) {
            log.error("Driver Documents is EMPTY");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_FILE_SIZE,
                                    MessageConstants.EMPTY_REQUEST));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_FILE_SIZE);
            return error;
        } else {
            addErrorForInvalidFileSize(file, error);
            addErrorForInvalidFileName(file.getName(), DOCUMENT_NAME, error);
        }
        return error;
    }

    private void addErrorForInvalidFileName(String docName, String paramName, ErrorInfo error) {
        if(Objects.isNull(docName) || (! docName.matches(DOCUMENT_NAME_REGEX))) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_NAME,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_NAME);

        }
    }

    private void addErrorForInvalidFileSize(MultipartFile file, ErrorInfo error) {

        long size = file.getSize() / (1000);
        if(size == 0 || size > maxFileUploadSize) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_NAME,
                                    MessageConstants.INVALID_FILE_SIZE
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_NAME);
        }

    }

    private ErrorDetails createErrorDetails(final String code, final String message )
    {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(code);
        errorDetails.setMessage(message);
        return errorDetails;
    }
}
