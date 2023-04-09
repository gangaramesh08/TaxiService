package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.SignUpInfo;
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
     * @param signUpInfo basic driver information for sign up
     * @return OTP is generated and send to the given mobile number for verification
     * @throws InvalidInputException when invalid data is provided exception is thrown
     */

    @PostMapping(value = "/signup")
    public ResponseEntity<Integer> signUp(@RequestBody SignUpInfo signUpInfo) throws InvalidInputException, PersistenceException {
       signUpService.validate(signUpInfo);
       Integer otp  = signUpService.signUp(signUpInfo);
       return ResponseEntity.ok(otp);
    }
}
