package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.loginservice.service.ISignUpService;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.test.taxiservice.loginservice.constants.Constants.*;

/**
 * Apis to perform signUp of drivers for the Taxi Service
 */
@RestController
@RequestMapping(LOGINSERVICE_API_URL)
@Slf4j
public class SignUpController {

    @Autowired
    private ISignUpService signUpService;

    /**
     * POST
     * /driver/loginservice/signup
     *
     * Api used for signing up a new driver.
     * @param signUpInfo basic driver information for sign up
     * @return OTP is generated and send to the given mobile number for verification
     * @throws InvalidInputException when invalid data is provided exception is thrown
     */

    @PostMapping(value = SIGNUP_API_URL)
    public ResponseEntity<Integer> signUp(@RequestBody SignUpInfo signUpInfo) throws InvalidInputException, PersistenceException {
       signUpService.validate(signUpInfo);
       Integer otp  = signUpService.signUp(signUpInfo);
       return ResponseEntity.ok(otp);
    }

    /**
     * GET
     * /driver/loginservice/validateotp
     * API used for validating the OTP entered by the driver.
     * @param mobileNumber
     * @param otp
     * @return
     */
    @GetMapping(value = VALIDATE_OTP_API_URL)
    public ResponseEntity<Boolean> validateOTP(@RequestParam String mobileNumber, @RequestParam Integer otp){
        return ResponseEntity.ok(signUpService.validateOTP(mobileNumber,otp));
    }

    /**
     * GET
     * /driver/loginservice/regenerateotp
     *
     * API used for regenerating the OTP for a given driver mobile Number
     * @param mobileNumber
     * @return
     */
    @GetMapping(value = REGENERATE_OTP_API_URL)
    public ResponseEntity<Integer> regenerateOtp(@RequestParam String mobileNumber){
        Integer otp = signUpService.regenerateOTP(mobileNumber);
        return ResponseEntity.ok(otp);
    }
}
