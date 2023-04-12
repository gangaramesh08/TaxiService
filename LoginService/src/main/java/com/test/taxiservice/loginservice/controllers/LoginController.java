package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.loginservice.service.ILoginService;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.test.taxiservice.loginservice.constants.Constants.LOGINSERVICE_API_URL;
import static com.test.taxiservice.loginservice.constants.Constants.LOGIN_API_URL;

/**
 * Apis to perform login of drivers for the Taxi Service. A token is generated during login
 * and is stored in Redis. This token is then used for authentication in all other requests.
 */
@RestController
@RequestMapping(LOGINSERVICE_API_URL)
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

    @PostMapping(value = LOGIN_API_URL)
    public ResponseEntity<String> login(@RequestBody DriverCredentials driverCredentials) throws InvalidInputException, PersistenceException {
        loginService.validateLogin(driverCredentials.getMobileNumber(), driverCredentials.getPassword());
        String token = loginService.login(driverCredentials);

        return ResponseEntity.ok(token);
    }
}
