package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.DriverInfo;
import org.springframework.stereotype.Service;

/**
 * Service for Signup activities
 */
@Service
public interface ISignUpService {

    /**
     * This method first generates an OTP that can then be send to the registered mobile number
     * for verification. This OTP is then saved into database so that it can be validated against the
     * OTP that user provides. The password enetered is also encrypted and all the data is saved in the
     * database.
     * @param driverInfo
     * @return OTP generated for verification
     * @throws PersistenceException thrown when any exception occurs during saving information into db
     */

    Integer signUp(DriverInfo driverInfo) throws PersistenceException;


    /**
     * This method tries to validate the user entered data.
     * @param driverInfo
     * @throws InvalidInputException thrown when data entered is not valid
     */
    void validate(DriverInfo driverInfo) throws InvalidInputException;
}
