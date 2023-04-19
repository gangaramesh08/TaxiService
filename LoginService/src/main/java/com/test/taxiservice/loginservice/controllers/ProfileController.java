package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.loginservice.service.IProfileService;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.test.taxiservice.loginservice.constants.Constants.*;

@RestController
@RequestMapping(LOGINSERVICE_API_URL)
@Slf4j
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    /**
     * POST
     * /driver/loginservice/profile/update
     *
     * API for adding or updating driver profile.
     *
     * @param driverProfileUpdate Request POJO to accept profile information
     * @return Success message (Status Code 200) in case of successful update
     * @throws PersistenceException when some error occurs in db transactions
     * @throws InvalidInputException when input is not valid
     */
    @PostMapping(PROFILE_UPDATE_API_URL)
    public ResponseEntity<String> updateProfile(@RequestBody DriverProfileUpdate driverProfileUpdate)
            throws PersistenceException, InvalidInputException {
        profileService.validateProfile(driverProfileUpdate);
        profileService.updateProfile(driverProfileUpdate);
        return ResponseEntity.ok(SUCCESS);

    }
}
