package com.test.taxiservice.loginservice.utils;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.constants.ResponseConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorDetails;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.ErrorResponse;
import com.test.taxiservice.loginservice.model.SignUpInfo;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
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
    private DriverCredentialsRepository credentialsRepository;

    public ErrorInfo validateDriverDetails(SignUpInfo signUpInfo) {
        ErrorResponse errorResponse = new ErrorResponse();
        ErrorInfo error = new ErrorInfo();
        error.setDetails(new ArrayList<>());
        errorResponse.setError(error);
        if(Objects.isNull(signUpInfo)) {
            log.error("Driver Information is EMPTY");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA,
                                    MessageConstants.EMPTY_REQUEST));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA);
            return error;
        } else{
            addErrorForInvalidName(signUpInfo.getFirstName(), FIRST_NAME, error);
            addErrorForInvalidName(signUpInfo.getLastName(), LAST_NAME, error);
            addErrorForDuplicateMobileNumber(signUpInfo.getMobileNumber(), MOBILE_NUMBER, error);
        }
        return error;
    }

    private void addErrorForDuplicateMobileNumber(String mobileNumber, String paramName, ErrorInfo error) {

        if(Objects.isNull(mobileNumber) || (!mobileNumber.matches(MOBILENUMBER_REGEX))) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER);
        } else if(!Objects.isNull(credentialsRepository.getByMobileNumber(mobileNumber))){
            log.error("Duplicate Mobile Number identified");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_MOBILE_NUMBER,
                                    MessageConstants.DUPLICATE_MOBILENUMBER));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_MOBILE_NUMBER);
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
