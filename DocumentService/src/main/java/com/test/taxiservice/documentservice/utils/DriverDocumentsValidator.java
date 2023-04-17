package com.test.taxiservice.documentservice.utils;

import com.test.taxiservice.documentservice.model.DriverBackgroundStatusEnum;
import com.test.taxiservice.documentservice.model.ExternalBGStatusRequest;
import com.test.taxiservice.documentservice.repository.DriverBackgroundStatusRepository;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorDetails;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.ErrorResponse;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DocumentType;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverBackgroundVerification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static com.test.taxiservice.documentservice.common.Constants.*;

@Component
@Slf4j
public class DriverDocumentsValidator {

    @Autowired
    private DriverBackgroundStatusRepository driverBackgroundStatusRepository;

    @Value("${document.max-file-size}")
    private long maxFileUploadSize;
    public ErrorInfo validateDriverDetails( MultipartFile file, String documentType) {
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
            addErrorForInvalidDocumentType(documentType, DOCUMENT_TYPE, error);
            addErrorForInvalidFileSize(file, error);
            addErrorForInvalidFileName(file.getName(), DOCUMENT_NAME, error);
        }
        return error;
    }

    private void addErrorForInvalidDocumentType(String documentType, String paramName, ErrorInfo error) {
        try {
            DocumentType.valueOf(documentType);
        }catch (Exception exception) {
            log.error("Invalid documentType : {}", documentType);
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_DOC_TYPE,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_DOC_TYPE);
        }
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

    public ErrorInfo validateExternalBGRequest(ExternalBGStatusRequest externalBGStatusRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        ErrorInfo error = new ErrorInfo();
        error.setDetails(new ArrayList<>());
        errorResponse.setError(error);
        if(Objects.isNull(externalBGStatusRequest)) {
            log.error("External BackgroundCheck request is EMPTY");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA,
                                    MessageConstants.EMPTY_REQUEST));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA);
            return error;
        } else {
            addErrorForInvalidVerificationId(externalBGStatusRequest.getVerificationId(), error);
            addErrorForIncompleteDocumentList(externalBGStatusRequest, error);
        }
        return error;
    }

    private void addErrorForIncompleteDocumentList(ExternalBGStatusRequest externalBGStatusRequest, ErrorInfo error) {
        if(externalBGStatusRequest.getStatus()
                .equalsIgnoreCase(DriverBackgroundStatusEnum.FAILED.getValue()) &&
                externalBGStatusRequest.getDocumentsList().isEmpty()) {
            log.error("External BackgroundCheck request is EMPTY");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_DOCUMENT_LIST,
                                    MessageConstants.INVALID_DOCUMENT_LIST));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_DOCUMENT_LIST);
        }
    }

    private void addErrorForInvalidVerificationId(BigInteger statusId, ErrorInfo error) {
        Optional<DriverBackgroundVerification> statusIdFromDb = driverBackgroundStatusRepository.findById(statusId);
        if(statusIdFromDb.isEmpty()) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_VERIFICATION_ID,
                                    MessageConstants.INVALID_VERIFICATION_ID
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_VERIFICATION_ID);
        }
    }
}
