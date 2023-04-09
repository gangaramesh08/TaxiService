package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.InvalidInputException;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.model.DriverInfo;
import com.test.taxiservice.loginservice.repository.DriverInfoRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.loginservice.utils.OtpGeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.test.taxiservice.loginservice.constants.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;


@Service
@Slf4j
public class SignUpService implements ISignUpService {

    @Autowired
    private DriverInfoRepository repository;

    @Autowired
    private DriverInfoValidator validator;

    @Override
    public Integer signUp(DriverInfo driverInfo) throws PersistenceException {
        Integer otp = OtpGeneratorUtil.generateOtp();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bCryptPasswordEncoder.encode(driverInfo.getPassword());
        driverInfo.setPassword(encoded);
        try {

            repository.save(driverInfo);

        } catch (Exception exception) {
            log.error("Duplicate phoneNumber identified. {}", exception.getMessage());
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
        return otp;
    }

    @Override
    public void validate(DriverInfo driverInfo) throws InvalidInputException {
        ErrorInfo validationError = validator.validateDriverDetails(driverInfo);
        if( !validationError.getDetails().isEmpty() ) {
            validationError.setMessage(MessageConstants.INVALID_DRIVER_INFO);
            validationError.getDetails().forEach(errorDetails -> log.error(errorDetails.getMessage()));
            throw new InvalidInputException(validationError);
        }
    }
}
