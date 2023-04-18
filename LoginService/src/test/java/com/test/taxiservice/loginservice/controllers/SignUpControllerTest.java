package com.test.taxiservice.loginservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.taxiservice.loginservice.service.SignUpService;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import com.test.taxiservice.taxiservicecommon.utils.TestFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.test.taxiservice.loginservice.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class SignUpControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    SignUpController signUpController;

    @Mock
    private SignUpService signUpService;


    @Test
    void testSignUpURLSuccess() throws Exception {
        String fileContentAsString = TestFileUtils.getFileContentAsString(VALID_SIGNUP_REQUEST);
        SignUpInfo signUpInfo = objectMapper.readValue(fileContentAsString, SignUpInfo.class);


        Mockito.doNothing().when(signUpService).validate(any());
        Mockito.when(signUpService.signUp(any())).thenReturn(VALID_OTP);
        ResponseEntity<Integer> response = signUpController.signUp(signUpInfo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_OTP, response.getBody());
    }

    @Test
    void testSignUpURLInvalid() throws Exception {
        String fileContentAsString = TestFileUtils.getFileContentAsString(INVALID_SIGNUP_REQUEST);
        SignUpInfo signUpInfo = objectMapper.readValue(fileContentAsString, SignUpInfo.class);

        Mockito.doThrow(InvalidInputException.class).when(signUpService).validate(any());
        assertThrows(InvalidInputException.class, ()-> signUpController.signUp(signUpInfo));
    }

    @Test
    void testValidateOtpSuccess() {
        Mockito.when(signUpService.validateOTP(anyString(), any())).thenReturn(true);
        ResponseEntity<Boolean> response = signUpController.validateOTP(VALID_MOBILE_NUMBER, VALID_OTP);
        assertEquals(Boolean.TRUE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testValidateOtpFailure() {
        Mockito.when(signUpService.validateOTP(anyString(), any())).thenReturn(false);
        ResponseEntity<Boolean> response = signUpController.validateOTP(VALID_MOBILE_NUMBER, INVALID_OTP);
        assertEquals(Boolean.FALSE, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRegenerateOtpSuccess() {
        Mockito.when(signUpService.regenerateOTP(anyString())).thenReturn(VALID_OTP);
        ResponseEntity<Integer> response = signUpController.regenerateOtp(VALID_MOBILE_NUMBER);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_OTP, response.getBody());
    }
}
