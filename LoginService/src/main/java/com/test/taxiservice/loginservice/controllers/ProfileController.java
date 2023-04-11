package com.test.taxiservice.loginservice.controllers;

import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.service.IProfileService;
import com.test.taxiservice.taxiservicecommon.model.loginservice.model.DriverProfileUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.test.taxiservice.loginservice.constants.Constants.LOGINSERVICE_API_URL;
import static com.test.taxiservice.loginservice.constants.Constants.PROFILE_UPDATE_API_URL;

@RestController
@RequestMapping(LOGINSERVICE_API_URL)
@Slf4j
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    @PostMapping(PROFILE_UPDATE_API_URL)
    public ResponseEntity<String> updateProfile(@RequestBody DriverProfileUpdate driverProfileUpdate) throws PersistenceException {
        profileService.updateProfile(driverProfileUpdate);
        return ResponseEntity.ok("SUCCESS");

    }
}
