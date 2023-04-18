package com.test.taxiservice.loginservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorDetails;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.DriverMobileId;
import com.test.taxiservice.taxiservicecommon.model.DriverSignUpOTP;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverCredentials;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import com.test.taxiservice.taxiservicecommon.repository.DriverSignUpOTPRepository;
import com.test.taxiservice.taxiservicecommon.service.DriverStatusService;
import com.test.taxiservice.taxiservicecommon.utils.TestFileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SignUpServiceTest {

    @Mock
    private DriverInfoValidator driverInfoValidator;

    @Mock
    private ProfileService profileService;

    @Mock
    private DriverStatusService driverStatusService;

    @Mock
    private DriverCredentialsRepository credentialsRepository;

    @Mock
    private DriverSignUpOTPRepository driverSignUpOTPRepository;

    @Mock
    private DriverMobileIdRepository driverMobileIdRepository;

    @InjectMocks
    private SignUpService signUpService;

    private  final String validMobileNumber = "1122334455";
    private  final String invalidMobileNumber = "1122334450";
    private  final Integer validOtp = 654327;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        when(credentialsRepository.save(Mockito.any(DriverCredentials.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(driverMobileIdRepository.save(Mockito.any(DriverMobileId.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(driverSignUpOTPRepository.save(Mockito.any(DriverSignUpOTP.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void testSignupSuccess() throws IOException, PersistenceException {
        String requestJson = TestFileUtils.getFileContentAsString("signup_success.json");
        SignUpInfo signUpInfo = objectMapper.readValue(requestJson, SignUpInfo.class);
        Mockito.doNothing().when(profileService).addProfile(any(DriverProfile.class));
        Mockito.doNothing().when(driverStatusService).updateStatus(any(), any());

        assertDoesNotThrow(()->signUpService.signUp(signUpInfo));
    }

    @Test
    void testSignupFailure() throws IOException {
        String requestJson = TestFileUtils.getFileContentAsString("signup_success.json");
        SignUpInfo signUpInfo = objectMapper.readValue(requestJson, SignUpInfo.class);
        Mockito.doThrow(RuntimeException.class).when(credentialsRepository).save(any());
        assertThrows(PersistenceException.class, ()->signUpService.signUp(signUpInfo));

    }

    @Test
    void testValidateSuccess() {
        SignUpInfo signUpInfo = new SignUpInfo();
        ErrorInfo error = new ErrorInfo();
        Mockito.when(driverInfoValidator.validateDriverDetails(any(SignUpInfo.class))).thenReturn(error);
        assertDoesNotThrow(()->signUpService.validate(signUpInfo));
    }

    @Test
    void testValidateFailure()  {
        SignUpInfo signUpInfo = new SignUpInfo();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_MOBILE_NUMBER);
        errorDetails.setMessage(MessageConstants.DUPLICATE_MOBILENUMBER);
        ErrorInfo error = new ErrorInfo();
        error.setDetails(List.of(errorDetails));
        Mockito.when(driverInfoValidator.validateDriverDetails(any(SignUpInfo.class))).thenReturn(error);
        assertThrows(InvalidInputException.class, ()->signUpService.validate(signUpInfo));

    }

    @Test
    void testValidateOTPSuccess() {
        DriverSignUpOTP driverSignUpOTP = new DriverSignUpOTP(validMobileNumber, validOtp);
        Mockito.when(driverSignUpOTPRepository.findById(validMobileNumber)).thenReturn(Optional.of(driverSignUpOTP));
        assertTrue(signUpService.validateOTP(validMobileNumber, validOtp));
    }

    @Test
    void testValidateOTPFailureForInvalidMobileNumber() {
        DriverSignUpOTP driverSignUpOTP = new DriverSignUpOTP(invalidMobileNumber, validOtp);
        Mockito.when(driverSignUpOTPRepository.findById(invalidMobileNumber)).thenReturn(Optional.of(driverSignUpOTP));
        assertTrue(signUpService.validateOTP(invalidMobileNumber, validOtp));
    }

    @Test
    void testValidateOTPFailureForInvalidOtp() {
        Integer invalidOtp = 174327;
        DriverSignUpOTP driverSignUpOTP = new DriverSignUpOTP(validMobileNumber, invalidOtp);
        Mockito.when(driverSignUpOTPRepository.findById(validMobileNumber)).thenReturn(Optional.of(driverSignUpOTP));
        assertTrue(signUpService.validateOTP(validMobileNumber, invalidOtp));
    }

    @Test
    void testRegenerateOtpSuccess() {
        Integer otp_New = signUpService.regenerateOTP(invalidMobileNumber);
        assertNotNull(otp_New);

    }
}
