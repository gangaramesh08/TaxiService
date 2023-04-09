package com.test.taxiservice.loginservice.utils;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.constants.ResponseConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorDetails;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.ErrorResponse;
import com.test.taxiservice.loginservice.model.DriverInfo;
import com.test.taxiservice.loginservice.repository.DriverInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

import static com.test.taxiservice.loginservice.constants.Constants.*;

@Component
@Slf4j
public class DriverInfoValidator {

    @Autowired
    private DriverInfoRepository repository;

    public ErrorInfo validateDriverDetails(DriverInfo driverInfo) {
        ErrorResponse errorResponse = new ErrorResponse();
        ErrorInfo error = new ErrorInfo();
        error.setDetails(new ArrayList<>());
        errorResponse.setError(error);
        if(Objects.isNull(driverInfo)) {
            log.error("Driver Information is NULL");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA,
                                    MessageConstants.EMPTY_REQUEST));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA);
            return error;
        } else{
            addErrorForInvalidName(driverInfo.getFirstName(), FIRST_NAME, error);
            addErrorForInvalidName(driverInfo.getLastName(), LAST_NAME, error);
            addErrorForDuplicatePhoneNumber(driverInfo.getPhoneNumber(), PHONE_NUMBER, error);
        }
        return error;
    }

    private void addErrorForDuplicatePhoneNumber(String phoneNumber, String paramName, ErrorInfo error) {

        if(Objects.isNull(phoneNumber) || (!phoneNumber.matches(PHONENUMBER_REGEX))) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_PHONE_NUMBER,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_PHONE_NUMBER);
        } else if(!Objects.isNull(repository.getByPhoneNumber(phoneNumber))){
            log.error("Duplicate Mobile Number identified");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_PHONE_NUMBER,
                                    MessageConstants.DUPLICATE_PHONENUMBER));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_PHONE_NUMBER);
        }
    }

    private void addErrorForInvalidName(String name,  final String paramName, ErrorInfo error) {
        if(Objects.isNull(name) || (! name.matches(NAME_REGEX))) {
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

    private ErrorDetails createErrorDetails(final String code, final String message )
    {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(code);
        errorDetails.setMessage(message);
        return errorDetails;
    }
}
