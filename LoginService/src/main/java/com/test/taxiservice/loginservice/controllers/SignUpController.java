package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.DriverInfo;
import com.test.taxiservice.loginservice.service.ISignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Apis to perform signUp of drivers for the Taxi Service
 */
@RestController
@Slf4j
public class SignUpController {

    @Autowired
    private ISignUpService signUpService;

    /**
     * Api used for signing up a new driver.
     * @param driverInfo basic dirver information
     * @return OTP is generated and send to the given mobile number for verification
     * @throws InvalidInputException when invalid data is provided exception is thrown
     */

    @PostMapping(value = "/signup")
    public ResponseEntity<Integer> signUp(@RequestBody DriverInfo driverInfo) throws InvalidInputException, PersistenceException {
       signUpService.validate(driverInfo);
       Integer otp  = signUpService.signUp(driverInfo);
       return ResponseEntity.ok(otp);
    }
}
