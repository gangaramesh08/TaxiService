package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.service.LoginService;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverCredentials;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.test.taxiservice.loginservice.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Test
    void tesLoginSuccess() throws InvalidInputException, PersistenceException {
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, VALID_PASSWORD);
        Mockito.doNothing().when(loginService).validateLogin(anyString(), anyString());
        Mockito.when(loginService.login(driverCredentials)).thenReturn(VALID_TOKEN);
        ResponseEntity<String> response = loginController.login(driverCredentials);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(VALID_TOKEN, response.getBody());
    }

    @Test
    void tesLoginFailureValidationFail() throws InvalidInputException, PersistenceException {
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, INVALID_PASSWORD);
        Mockito.doThrow(InvalidInputException.class).when(loginService).validateLogin(VALID_MOBILE_NUMBER, INVALID_PASSWORD);
        assertThrows(InvalidInputException.class, ()->loginController.login(driverCredentials));
    }

    @Test
    void tesLoginFailureLoginFail() throws InvalidInputException, PersistenceException {
        DriverCredentials driverCredentials = new DriverCredentials(VALID_DRIVER_ID, VALID_MOBILE_NUMBER, INVALID_PASSWORD);
        Mockito.doNothing().when(loginService).validateLogin(anyString(), anyString());
        Mockito.doThrow(PersistenceException.class).when(loginService).login(driverCredentials);
        assertThrows(PersistenceException.class, ()->loginController.login(driverCredentials));
    }
}
