package com.test.taxiservice.loginservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.model.DriverMobileId;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import com.test.taxiservice.taxiservicecommon.utils.TestFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.test.taxiservice.loginservice.common.TestConstants.*;
import static com.test.taxiservice.loginservice.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class DriverInfoValidatorTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private DriverInfoValidator driverInfoValidator;

    @Mock
    private DriverCredentialsRepository credentialsRepository;

    @Mock
    private DriverMobileIdRepository driverMobileIdRepository;

    @Test
    void testValidateDriverDetailsSuccess() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_SIGNUP_REQUEST);
        SignUpInfo signUpInfo = objectMapper.readValue(requestJson, SignUpInfo.class);
        Mockito.when(credentialsRepository.getByMobileNumber(anyString())).thenReturn(null);

        ErrorInfo errorInfo = driverInfoValidator.validateDriverDetails(signUpInfo);
        assertTrue(errorInfo.getDetails().isEmpty());
    }

    @Test
    void testValidaDriverDetailsWithRequestEmpty() {
        ErrorInfo errorInfo = driverInfoValidator.validateDriverDetails(null);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_NO_DATA, errorInfo.getDetails().get(0).getCode());
        assertEquals(MessageConstants.EMPTY_REQUEST, errorInfo.getDetails().get(0).getMessage());
    }

    @Test
    void testValidaDriverDetailsWithInvalidName() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_SIGNUP_REQUEST);
        SignUpInfo signUpInfo = objectMapper.readValue(requestJson, SignUpInfo.class);
        signUpInfo.setFirstName("M@nu");
        Mockito.when(credentialsRepository.getByMobileNumber(anyString())).thenReturn(null);
        ErrorInfo errorInfo = driverInfoValidator.validateDriverDetails(signUpInfo);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_NAME, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_NAME, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, FIRST_NAME), errorInfo.getDetails().get(0).getMessage());
    }

    @Test
    void testValidaDriverDetailsWithDuplicateMobileNumber() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_SIGNUP_REQUEST);
        SignUpInfo signUpInfo = objectMapper.readValue(requestJson, SignUpInfo.class);
        Mockito.when(credentialsRepository.getByMobileNumber(anyString())).thenReturn(signUpInfo.getMobileNumber());
        ErrorInfo errorInfo = driverInfoValidator.validateDriverDetails(signUpInfo);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_MOBILE_NUMBER, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_MOBILE_NUMBER, errorInfo.getDetails().get(0).getCode());
        assertEquals(MessageConstants.DUPLICATE_MOBILENUMBER, errorInfo.getDetails().get(0).getMessage());
    }

    @Test
    void testValidaDriverDetailsWithInvalidMobileNumber() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_SIGNUP_REQUEST);
        SignUpInfo signUpInfo = objectMapper.readValue(requestJson, SignUpInfo.class);
        signUpInfo.setMobileNumber(INVALID_MOBILE_NUMBER);
        ErrorInfo errorInfo = driverInfoValidator.validateDriverDetails(signUpInfo);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, MOBILE_NUMBER), errorInfo.getDetails().get(0).getMessage());
    }

    @Test
    void testValidateDriverProfileSuccess() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST);
        DriverProfileUpdate driverProfileUpdate = objectMapper.readValue(requestJson, DriverProfileUpdate.class);
        DriverMobileId driverMobileId = new DriverMobileId(driverProfileUpdate.getMobileNumber(), VALID_DRIVER_ID);
        Mockito.when(driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber())).thenReturn(Optional.of(driverMobileId));

        ErrorInfo errorInfo = driverInfoValidator.validateDriverProfile(driverProfileUpdate);
        assertTrue(errorInfo.getDetails().isEmpty());

    }

    @Test
    void testValidateDriverProfileInvalidMobileNumber() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST);
        DriverProfileUpdate driverProfileUpdate = objectMapper.readValue(requestJson, DriverProfileUpdate.class);

        driverProfileUpdate.setMobileNumber(INVALID_MOBILE_NUMBER);
        ErrorInfo errorInfo = driverInfoValidator.validateDriverProfile(driverProfileUpdate);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_MOBILE_NUMBER, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, MOBILE_NUMBER), errorInfo.getDetails().get(0).getMessage());

    }

    @Test
    void testValidateDriverProfileInvalidAge() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST);
        DriverProfileUpdate driverProfileUpdate = objectMapper.readValue(requestJson, DriverProfileUpdate.class);
        DriverMobileId driverMobileId = new DriverMobileId(driverProfileUpdate.getMobileNumber(), VALID_DRIVER_ID);
        Mockito.when(driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber())).thenReturn(Optional.of(driverMobileId));
        driverProfileUpdate.setAge(200);
        ErrorInfo errorInfo = driverInfoValidator.validateDriverProfile(driverProfileUpdate);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_AGE, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_AGE, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, AGE), errorInfo.getDetails().get(0).getMessage());

    }

    @Test
    void testValidateDriverProfileInvalidEmail() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST);
        DriverProfileUpdate driverProfileUpdate = objectMapper.readValue(requestJson, DriverProfileUpdate.class);
        DriverMobileId driverMobileId = new DriverMobileId(driverProfileUpdate.getMobileNumber(), VALID_DRIVER_ID);
        Mockito.when(driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber())).thenReturn(Optional.of(driverMobileId));
        driverProfileUpdate.setEmail("abcde");
        ErrorInfo errorInfo = driverInfoValidator.validateDriverProfile(driverProfileUpdate);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_EMAIL, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_EMAIL, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, EMAIL), errorInfo.getDetails().get(0).getMessage());

    }

    @Test
    void testValidateDriverProfileInvalidAddress() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST);
        DriverProfileUpdate driverProfileUpdate = objectMapper.readValue(requestJson, DriverProfileUpdate.class);
        DriverMobileId driverMobileId = new DriverMobileId(driverProfileUpdate.getMobileNumber(), VALID_DRIVER_ID);
        Mockito.when(driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber())).thenReturn(Optional.of(driverMobileId));
        driverProfileUpdate.setAddressList(List.of());
        ErrorInfo errorInfo = driverInfoValidator.validateDriverProfile(driverProfileUpdate);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, ADDRESS), errorInfo.getDetails().get(0).getMessage());

    }

    @Test
    void testValidateDriverProfileInvalidAddress2() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString(INVALID_PROFILE_UPDATE_REQUEST);
        DriverProfileUpdate driverProfileUpdate = objectMapper.readValue(requestJson, DriverProfileUpdate.class);
        DriverMobileId driverMobileId = new DriverMobileId(driverProfileUpdate.getMobileNumber(), VALID_DRIVER_ID);
        Mockito.when(driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber())).thenReturn(Optional.of(driverMobileId));
        ErrorInfo errorInfo = driverInfoValidator.validateDriverProfile(driverProfileUpdate);
        assertFalse(errorInfo.getDetails().isEmpty());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS, errorInfo.getCode());
        assertEquals(ResponseConstants.CODE_ERROR_BADREQUEST_INVALID_ADDRESS, errorInfo.getDetails().get(0).getCode());
        assertEquals(String.format(MessageConstants.INVALID_VALUE, ADDRESS), errorInfo.getDetails().get(0).getMessage());

    }


}
