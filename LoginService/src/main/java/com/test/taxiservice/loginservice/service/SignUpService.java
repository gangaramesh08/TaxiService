package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.loginservice.repository.DriverCredentialsRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.loginservice.utils.OtpGeneratorUtil;
import com.test.taxiservice.taxiservicecommon.model.DriverMobileId;
import com.test.taxiservice.taxiservicecommon.model.DriverSignUpOTP;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverCredentials;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.SignUpInfo;
import com.test.taxiservice.taxiservicecommon.repository.DriverAccessTokenRepository;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import com.test.taxiservice.taxiservicecommon.repository.DriverSignUpOTPRepository;
import com.test.taxiservice.taxiservicecommon.service.DriverStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static com.test.taxiservice.taxiservicecommon.common.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;


@Service
@Slf4j
public class SignUpService implements ISignUpService {

    @Autowired
    private DriverCredentialsRepository credentialsRepository;

    @Autowired
    private DriverMobileIdRepository driverMobileIdRepository;


    @Autowired
    private DriverSignUpOTPRepository driverSignUpOTPRepository;

    @Autowired
    DriverAccessTokenRepository driverAccessTokenRepository;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private DriverStatusService driverStatusService;

    @Autowired
    private DriverInfoValidator validator;

    @Override
    public Integer signUp(SignUpInfo signUpInfo) throws PersistenceException {
        Integer otp = OtpGeneratorUtil.generateOtp();

        try {

            DriverCredentials credentials = new DriverCredentials();
            credentials.setMobileNumber(signUpInfo.getMobileNumber());
            credentials.setPassword(Base64.getEncoder().encodeToString(signUpInfo.getPassword().getBytes()));
            credentials.setCreatedAt(new Date());
            credentials.setModifiedAt(credentials.getCreatedAt());
            credentialsRepository.save(credentials);

            driverMobileIdRepository.save(new DriverMobileId(credentials.getMobileNumber(), credentials.getDriverId()));
            DriverProfile driverProfile = new DriverProfile();
            driverProfile.setDriverId(credentials.getDriverId());
            driverProfile.setCreatedAt(new Date());
            driverProfile.setModifiedAt(driverProfile.getCreatedAt());
            driverProfile.setFirstName(signUpInfo.getFirstName());
            driverProfile.setLastName(signUpInfo.getLastName());
            profileService.addProfile(driverProfile);
            driverStatusService.updateStatus(credentials.getDriverId(), DriverStatusEnum.PROFILE_UPDATE_IN_PROGRESS);

            DriverSignUpOTP driverSignUpOTP = new DriverSignUpOTP(
                    credentials.getMobileNumber(),
                    otp);
            driverSignUpOTPRepository.save(driverSignUpOTP);


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

    @Override
    public boolean validateOTP(String mobileNumber, Integer otp) {

        Optional<DriverSignUpOTP> otpForMobileNumber = driverSignUpOTPRepository.findById(mobileNumber);
        return otpForMobileNumber.filter(driverSignUpOTP -> otp.equals(driverSignUpOTP.getOtp())).isPresent();
    }

    @Override
    public Integer regenerateOTP(String mobileNumber) {
        Integer otp = OtpGeneratorUtil.generateOtp();

        DriverSignUpOTP driverSignUpOTP = new DriverSignUpOTP(
                mobileNumber,
                otp);
        driverSignUpOTPRepository.save(driverSignUpOTP);
        return otp;
    }

}
