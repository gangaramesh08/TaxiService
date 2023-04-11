package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.loginservice.model.DriverCredentials;
import org.springframework.stereotype.Service;

@Service
public interface ILoginService {

    /**
     * This method validates the credentials entered by the driver.
     * @param mobileNumber mobileNumber
     * @param password password
     * @throws InvalidInputException
     * @throws PersistenceException
     */
    void validateLogin(String mobileNumber, String password) throws InvalidInputException, PersistenceException;

    String login(DriverCredentials driverCredentials);
}
