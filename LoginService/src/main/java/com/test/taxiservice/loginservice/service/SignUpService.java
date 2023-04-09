package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.DriverCredentials;
import com.test.taxiservice.loginservice.model.DriverProfile;
import com.test.taxiservice.loginservice.model.SignUpInfo;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.loginservice.repository.DriverProfileRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.loginservice.utils.OtpGeneratorUtil;
import com.test.taxiservice.loginservice.utils.PasswordEncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.test.taxiservice.loginservice.constants.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;


@Service
@Slf4j
public class SignUpService implements ISignUpService {

    @Autowired
    private DriverProfileRepository profileRepository;

    @Autowired
    private DriverCredentialsRepository credentialsRepository;

    @Autowired
    private DriverInfoValidator validator;

    @Override
    public Integer signUp(SignUpInfo signUpInfo) throws PersistenceException {
        Integer otp = OtpGeneratorUtil.generateOtp();

        try {
            DriverCredentials credentials = new DriverCredentials();
            credentials.setMobileNumber(signUpInfo.getMobileNumber());
            credentials.setPassword(PasswordEncryptionUtil.getEncryptedPassword(signUpInfo.getPassword()));
            credentials.setCreatedAt(new Date());
            credentials.setModifiedAt(credentials.getCreatedAt());
            credentialsRepository.save(credentials);


            DriverProfile driverProfile = new DriverProfile();
            driverProfile.setDriverId(credentials.getDriverId());
            driverProfile.setCreatedAt(new Date());
            driverProfile.setModifiedAt(driverProfile.getCreatedAt());
            driverProfile.setFirstName(signUpInfo.getFirstName());
            driverProfile.setLastName(signUpInfo.getLastName());
            profileRepository.save(driverProfile);


        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database");
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
        return otp;
    }

    @Override
    public void validate(SignUpInfo signUpInfo) throws InvalidInputException {
        ErrorInfo validationError = validator.validateDriverDetails(signUpInfo);
        if( !validationError.getDetails().isEmpty() ) {
            validationError.setMessage(MessageConstants.INVALID_DRIVER_INFO);
            validationError.getDetails().forEach(errorDetails -> log.error(errorDetails.getMessage()));
            throw new InvalidInputException(validationError);
        }
    }
}
