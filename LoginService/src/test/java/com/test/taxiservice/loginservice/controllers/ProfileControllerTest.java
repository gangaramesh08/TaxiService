package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.service.ProfileService;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.test.taxiservice.loginservice.common.TestConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    @Test
    void testUpdateProfileSuccess() throws InvalidInputException, PersistenceException {
        Mockito.doNothing().when(profileService).validateProfile(any());
        Mockito.doNothing().when(profileService).updateProfile(any());
        DriverProfileUpdate driverProfileUpdate = DriverProfileUpdate.builder().build();
        ResponseEntity<String> response = profileController.updateProfile(driverProfileUpdate);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(SUCCESS,response.getBody());
    }

    @Test
    void testUpdateProfileFailureValidationFail() throws InvalidInputException {
        Mockito.doThrow(InvalidInputException.class).when(profileService).validateProfile(any());
        DriverProfileUpdate driverProfileUpdate = DriverProfileUpdate.builder().build();
        assertThrows(InvalidInputException.class, ()-> profileController.updateProfile(driverProfileUpdate));
    }

    @Test
    void testUpdateProfileFailureProfileAddFail() throws InvalidInputException, PersistenceException {
        Mockito.doNothing().when(profileService).validateProfile(any());
        Mockito.doThrow(PersistenceException.class).when(profileService).updateProfile(any());
        DriverProfileUpdate driverProfileUpdate = new DriverProfileUpdate();
        assertThrows(PersistenceException.class, ()-> profileController.updateProfile(driverProfileUpdate));
    }
}
