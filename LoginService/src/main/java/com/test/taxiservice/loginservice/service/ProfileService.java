package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.constants.MessageConstants;
import com.test.taxiservice.loginservice.exceptions.ErrorInfo;
import com.test.taxiservice.loginservice.exceptions.PersistenceException;
import com.test.taxiservice.loginservice.repository.DriverProfileRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.loginservice.utils.NullAwareBeanUtilsBean;
import com.test.taxiservice.taxiservicecommon.model.loginservice.model.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.model.DriverProfileUpdate;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;

import static com.test.taxiservice.loginservice.constants.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;

@Service
@Slf4j
public class ProfileService implements IProfileService {

    @Autowired
    private DriverProfileRepository driverProfileRepository;

    @Autowired
    private DriverMobileIdRepository driverMobileIdRepository;

    @Autowired
    private DriverInfoValidator validator;
    @Autowired
    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    @Override
    public void updateProfile(DriverProfileUpdate driverProfileUpdate) throws PersistenceException {
        try {
            BigInteger driverId = driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber()).get().getDriverId();
            DriverProfile driverProfile = driverProfileRepository.findByDriverId(driverId);
            if(driverProfile == null) {
                driverProfile = new DriverProfile();
            } else {
                nullAwareBeanUtilsBean.copyProperty(driverProfileUpdate, driverProfile);
            }
            driverProfile.setModifiedAt(new Date());

            driverProfileRepository.save(driverProfile);
        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database");
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
    }
}
