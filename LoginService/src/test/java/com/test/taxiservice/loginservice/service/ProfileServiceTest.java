package com.test.taxiservice.loginservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.taxiservice.loginservice.repository.DriverProfileRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.loginservice.utils.NullAwareBeanUtilsBean;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorDetails;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.DriverMobileId;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import com.test.taxiservice.taxiservicecommon.repository.DriverAddressRepository;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import com.test.taxiservice.taxiservicecommon.service.IDriverStatusService;
import com.test.taxiservice.taxiservicecommon.utils.TestFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.test.taxiservice.loginservice.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private DriverProfileRepository driverProfileRepository;

    @Mock
    private DriverAddressRepository driverAddressRepository;

    @Mock
    private DriverMobileIdRepository driverMobileIdRepository;

    @Mock
    private IDriverStatusService driverStatusService;

    @Mock
    private DriverInfoValidator driverInfoValidator;
    @Spy
    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    @Test
    void testUpdateProfileSuccess() throws IOException, PersistenceException {
        DriverMobileId driverMobileId = new DriverMobileId(VALID_MOBILE_NUMBER, VALID_DRIVER_ID);
        when(driverMobileIdRepository.findById(anyString())).thenReturn(Optional.of(driverMobileId));
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setDriverId(VALID_DRIVER_ID);
        when(driverProfileRepository.findByDriverId(any())).thenReturn(driverProfile);
        when(driverAddressRepository.findByDriverId(VALID_DRIVER_ID)).thenReturn(null);
        DriverProfileUpdate driverProfileUpdate  = objectMapper.readValue(
                TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST), DriverProfileUpdate.class);
        assertDoesNotThrow(()->profileService.updateProfile(driverProfileUpdate));
    }

    @Test
    void testUpdateProfileFailWhenNoValidMobileNumber() throws IOException {
        when(driverMobileIdRepository.findById(anyString())).thenReturn(Optional.empty());
        DriverProfileUpdate driverProfileUpdate  = objectMapper.readValue(
                TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST), DriverProfileUpdate.class);
        assertThrows(PersistenceException.class, ()->profileService.updateProfile(driverProfileUpdate));
    }

    @Test
    void testUpdateProfilePersistenceFail() throws IOException {
        DriverMobileId driverMobileId = new DriverMobileId(VALID_MOBILE_NUMBER, VALID_DRIVER_ID);
        when(driverMobileIdRepository.findById(anyString())).thenReturn(Optional.of(driverMobileId));
        DriverProfile driverProfile = new DriverProfile();
        driverProfile.setDriverId(VALID_DRIVER_ID);
        when(driverProfileRepository.findByDriverId(any())).thenReturn(driverProfile);
        when(driverAddressRepository.findByDriverId(VALID_DRIVER_ID)).thenReturn(null);
        Mockito.doThrow(RuntimeException.class).when(driverAddressRepository).saveAll(any());
        DriverProfileUpdate driverProfileUpdate  = objectMapper.readValue(
                TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST), DriverProfileUpdate.class);
        assertThrows(PersistenceException.class, ()->profileService.updateProfile(driverProfileUpdate));
    }

    @Test
    void testUpdateProfileFailWhenNoProfileId() throws IOException {
        DriverMobileId driverMobileId = new DriverMobileId(VALID_MOBILE_NUMBER, VALID_DRIVER_ID);
        when(driverMobileIdRepository.findById(anyString())).thenReturn(Optional.of(driverMobileId));
        when(driverProfileRepository.findByDriverId(any())).thenReturn(null);
        DriverProfileUpdate driverProfileUpdate  = objectMapper.readValue(
                TestFileUtils.getFileContentAsString(VALID_PROFILE_UPDATE_REQUEST), DriverProfileUpdate.class);
        assertThrows(PersistenceException.class, ()->profileService.updateProfile(driverProfileUpdate));

    }

    @Test
    void testValidateSuccess() {
        DriverProfileUpdate driverProfileUpdate = new DriverProfileUpdate();
        ErrorInfo error = new ErrorInfo();
        Mockito.when(driverInfoValidator.validateDriverProfile(any(DriverProfileUpdate.class))).thenReturn(error);
        assertDoesNotThrow(()->profileService.validateProfile(driverProfileUpdate));
    }

    @Test
    void testValidateFailure()  {
        DriverProfileUpdate driverProfileUpdate = new DriverProfileUpdate();
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setCode(ResponseConstants.CODE_ERROR_BADREQUEST_DUPLICATE_MOBILE_NUMBER);
        errorDetails.setMessage(MessageConstants.DUPLICATE_MOBILENUMBER);
        ErrorInfo error = new ErrorInfo();
        error.setDetails(List.of(errorDetails));
        Mockito.when(driverInfoValidator.validateDriverProfile(any(DriverProfileUpdate.class))).thenReturn(error);
        assertThrows(InvalidInputException.class, ()->profileService.validateProfile(driverProfileUpdate));

    }

    @Test
    void testAddProfileSuccess() {
        when(driverProfileRepository.save(Mockito.any(DriverProfile.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        DriverProfile driverProfile = new DriverProfile();
        assertDoesNotThrow(()-> profileService.addProfile(driverProfile));
    }

    @Test
    void testAddProfileFailure() {
        Mockito.doThrow(RuntimeException.class).when(driverProfileRepository).save(any());
        DriverProfile driverProfile = new DriverProfile();
        assertThrows(PersistenceException.class, ()-> profileService.addProfile(driverProfile));
    }
}
