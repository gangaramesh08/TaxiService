package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.model.DriverProfileUpdate;
import org.springframework.stereotype.Service;

@Service
public interface IProfileService {
    void updateProfile(DriverProfileUpdate driverProfileUpdate) throws PersistenceException;
}
