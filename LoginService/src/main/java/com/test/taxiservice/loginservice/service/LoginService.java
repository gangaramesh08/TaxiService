package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.DriverProfile;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.loginservice.utils.PasswordEncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.test.taxiservice.loginservice.constants.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;

@Service
@Slf4j
public class LoginService implements ILoginService{

    @Autowired
    DriverCredentialsRepository credentialsRepository;

    @Override
    public void validateLogin(String mobileNumber, String password) throws InvalidInputException, PersistenceException {
        String encryptedPassword = PasswordEncryptionUtil.getEncryptedPassword(password);
        DriverProfile driverProfile;
        try {
            driverProfile =  credentialsRepository.getByMobileNumberAndPassword(mobileNumber, encryptedPassword);
        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database");
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
        if(Objects.isNull(driverProfile)) {
            log.error("Incorrect login credentials");
            ErrorInfo validationError = new ErrorInfo();
            validationError.setMessage(MessageConstants.INVALID_DRIVER_INFO);
            throw new InvalidInputException(validationError);
        }

    }
}
