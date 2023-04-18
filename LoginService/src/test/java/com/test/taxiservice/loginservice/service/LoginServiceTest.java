package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.DriverAccessToken;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverCredentials;
import com.test.taxiservice.taxiservicecommon.repository.DriverAccessTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.test.taxiservice.loginservice.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private DriverCredentialsRepository credentialsRepository;

    @Mock
    DriverAccessTokenRepository driverAccessTokenRepository;

    @Test
    void testValidateLoginSuccess(){
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, VALID_PASSWORD);
        when(credentialsRepository.getByMobileNumberAndPassword(anyString(), anyString())).thenReturn(driverCredentials);
        assertDoesNotThrow(()->loginService.validateLogin(VALID_MOBILE_NUMBER, VALID_PASSWORD));
    }

    @Test
    void testValidateLoginFailure() {
        Mockito.doThrow(RuntimeException.class).when(credentialsRepository).getByMobileNumberAndPassword(anyString(), anyString());
        assertThrows(PersistenceException.class,()->loginService.validateLogin(VALID_MOBILE_NUMBER, VALID_PASSWORD));
    }

    @Test
    void testValidateLoginFailureValidationFail(){
        when(credentialsRepository.getByMobileNumberAndPassword(anyString(), anyString())).thenReturn(null);
        assertThrows(InvalidInputException.class, ()->loginService.validateLogin(INVALID_MOBILE_NUMBER, VALID_PASSWORD));
    }

    @Test
    void testLoginSuccess() {
        ReflectionTestUtils.setField(loginService, "secretKey", "value");
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, VALID_PASSWORD);
        when(driverAccessTokenRepository.save(Mockito.any(DriverAccessToken.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        assertDoesNotThrow(()->loginService.login(driverCredentials));
    }

    @Test
    void testLoginFailure() {
        ReflectionTestUtils.setField(loginService, "secretKey", "value");
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, VALID_PASSWORD);
        Mockito.doThrow(RuntimeException.class).when(driverAccessTokenRepository).save(any());
        assertThrows(PersistenceException.class, ()->loginService.login(driverCredentials));
    }

    @Test
    void testLoginTokenFailure() {
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, VALID_PASSWORD);
        assertThrows(PersistenceException.class, ()->loginService.login(driverCredentials));
    }
}
