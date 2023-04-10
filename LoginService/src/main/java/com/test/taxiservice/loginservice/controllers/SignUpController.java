package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.SignUpInfo;
import com.test.taxiservice.loginservice.service.ISignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * API used for validating the OTP entered by the driver.
     * @param mobileNumber
     * @param otp
     * @return
     */
    @GetMapping(value = "/validateOTP")
    public ResponseEntity<Boolean> validateOTP(@RequestParam String mobileNumber, @RequestParam Integer otp){
        return ResponseEntity.ok(signUpService.validateOTP(mobileNumber,otp));
    }

    /**
     * API used for regenerating the OTP for a given driver mobile Number
     * @param mobileNumber
     * @return
     */
    @GetMapping(value = "/regenerateOTP")
    public ResponseEntity<Integer> validateOTP(@RequestParam String mobileNumber){
        Integer otp = signUpService.regenerateOTP(mobileNumber);
        return ResponseEntity.ok(otp);
    }
}
