package com.test.taxiservice.loginservice.utils;

import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.loginservice.repository.DriverProfileRepository;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorDetails;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.ErrorResponse;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import com.test.taxiservice.taxiservicecommon.model.trackingservice.DriverAddress;
import com.test.taxiservice.taxiservicecommon.model.trackingservice.DriverAddressTypeEnum;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.test.taxiservice.loginservice.constants.Constants.*;

@Component
@Slf4j
public class DriverInfoValidator {

    @Autowired
    private DriverCredentialsRepository credentialsRepository;

    @Autowired
    private DriverMobileIdRepository driverMobileIdRepository;

    @Autowired
    private DriverProfileRepository driverProfileRepository;

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

    public ErrorInfo validateDriverProfile(DriverProfileUpdate driverProfileUpdate) {
        ErrorResponse errorResponse = new ErrorResponse();
        ErrorInfo error = new ErrorInfo();
        error.setDetails(new ArrayList<>());
        errorResponse.setError(error);
        if(Objects.isNull(driverProfileUpdate)) {
            log.error("Driver Profile is EMPTY");
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA,
                                    MessageConstants.EMPTY_REQUEST));
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA);
            return error;
        } else{
            addErrorForInvalidMobileNumber(driverProfileUpdate.getMobileNumber(), MOBILE_NUMBER, error);
            if(driverProfileUpdate.getFirstName()!=null)
                addErrorForInvalidName(driverProfileUpdate.getFirstName(), FIRST_NAME, error);
            if(driverProfileUpdate.getLastName()!=null)
                addErrorForInvalidName(driverProfileUpdate.getLastName(), LAST_NAME, error);
            addErrorForInvalidAge(driverProfileUpdate.getAge(), AGE, error);
            addErrorForInvalidEmail(driverProfileUpdate.getEmail(), EMAIL, error);
            addErrorForInvalidAddress(driverProfileUpdate.getAddressList(), ADDRESS, error);


        }
        return error;
    }

    private void addErrorForInvalidMobileNumber(String mobileNumber, String paramName, ErrorInfo error) {
        if(driverMobileIdRepository.findById(mobileNumber).isEmpty() &&
            credentialsRepository.findByMobileNumber(mobileNumber) == null) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER);
        }
    }


    private void addErrorForInvalidAddress(List<DriverAddress> addressList, String paramName, ErrorInfo error) {
        if(addressList == null || addressList.size() != 2) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS);
        } else {
            long count = addressList.stream().filter(e ->
                    e.getCity() != null
                            && e.getZipCode() != null
                            && e.getCountry() != null
                            && e.getState() != null
                            && (e.getType().equalsIgnoreCase(DriverAddressTypeEnum.PERMANENT.getValue()))).distinct().count()
                    + addressList.stream().filter(e ->
                    e.getCity() != null
                            && e.getZipCode() != null
                            && e.getCountry() != null
                            && e.getState() != null
                            && (e.getType().equalsIgnoreCase(DriverAddressTypeEnum.CURRENT.getValue()))).distinct().count();

            if(count!=2) {
                error
                        .addDetailsItem(
                                createErrorDetails(
                                        ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS,
                                        String.format(MessageConstants.INVALID_VALUE, paramName)
                                )
                        );
                error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS);

            }
        }

    }

    private void addErrorForInvalidEmail(String email, String paramName, ErrorInfo error) {
        if(email==null || !email.matches(EMAIL_REGEX)) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_EMAIL,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_EMAIL);
        }
    }

    private void addErrorForInvalidAge(int age, String paramName, ErrorInfo error) {
        if(age<0 || age>100) {
            error
                    .addDetailsItem(
                            createErrorDetails(
                                    ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_AGE,
                                    String.format(MessageConstants.INVALID_VALUE, paramName)
                            )
                    );
            error.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_AGE);
        }
    }
}
