package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.DriverCredentials;
import com.test.taxiservice.loginservice.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Apis to perform login of drivers for the Taxi Service. A token is generated during login
 * and is stored in Redis. This token is then used for authentication in all other requests.
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private ILoginService loginService;

    /**
     * Api used for login of driver.
     * @param driverCredentials driverCredentials
     * @return OTP is generated and send to the given mobile number for verification
     * @throws InvalidInputException when invalid data is provided exception is thrown
     */

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody DriverCredentials driverCredentials) throws InvalidInputException, PersistenceException {
        loginService.validateLogin(driverCredentials.getMobileNumber(), driverCredentials.getPassword());

        return (ResponseEntity<Void>) ResponseEntity.ok();
    }
}
