package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import org.springframework.stereotype.Service;

@Service
public interface IProfileService {
    void updateProfile(DriverProfileUpdate driverProfileUpdate) throws PersistenceException;

    void addProfile(DriverProfile driverProfile) throws PersistenceException;

    void validateProfile(DriverProfileUpdate driverProfileUpdate) throws InvalidInputException;

}
