package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import org.springframework.stereotype.Service;

@Service
public interface IProfileService {
    void updateProfile(DriverProfileUpdate driverProfileUpdate) throws PersistenceException;
}
