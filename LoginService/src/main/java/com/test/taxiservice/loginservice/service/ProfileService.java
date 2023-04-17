package com.test.taxiservice.loginservice.service;

import com.test.taxiservice.loginservice.repository.DriverProfileRepository;
import com.test.taxiservice.loginservice.utils.DriverInfoValidator;
import com.test.taxiservice.loginservice.utils.NullAwareBeanUtilsBean;
import com.test.taxiservice.taxiservicecommon.common.MessageConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatusEnum;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfileUpdate;
import com.test.taxiservice.taxiservicecommon.model.trackingservice.DriverAddress;
import com.test.taxiservice.taxiservicecommon.repository.DriverAddressRepository;
import com.test.taxiservice.taxiservicecommon.repository.DriverMobileIdRepository;
import com.test.taxiservice.taxiservicecommon.service.IDriverStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.test.taxiservice.taxiservicecommon.common.ResponseConstants.CODE_ERROR_PERSISTENCE_ERROR;

@Service
@Slf4j
public class ProfileService implements IProfileService {

    @Autowired
    private DriverProfileRepository driverProfileRepository;

    @Autowired
    private DriverAddressRepository driverAddressRepository;

    @Autowired
    private DriverMobileIdRepository driverMobileIdRepository;

    @Autowired
    private IDriverStatusService driverStatusService;

    @Autowired
    private DriverInfoValidator validator;
    @Autowired
    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    @Override
    public void updateProfile(DriverProfileUpdate driverProfileUpdate) throws PersistenceException {
        try {
            BigInteger driverId = driverMobileIdRepository.findById(driverProfileUpdate.getMobileNumber()).get().getDriverId();
            DriverProfile driverProfile = driverProfileRepository.findByDriverId(driverId);
            List<DriverAddress> driverAddressList = driverAddressRepository.findByDriverId(driverId);

            nullAwareBeanUtilsBean.copyProperty(driverProfileUpdate, driverProfile);

            if(driverAddressList == null) {
                driverAddressList = new ArrayList<>();

            }
            for(DriverAddress address: driverProfileUpdate.getAddressList()){
                address.setDriverId(driverId);
                address.setCreatedAt(new Date());
                address.setModifiedAt(new Date());
                driverAddressList.add(address);
            }
            driverAddressRepository.saveAll(driverAddressList);
            driverProfile.setModifiedAt(new Date());
            driverProfileRepository.save(driverProfile);
            driverStatusService.updateStatus(driverId, DriverStatusEnum.PROFILE_UPDATE_COMPLETED);
        } catch (Exception exception) {
            log.error("Exception occurred while fetching driver information from database");
            ErrorInfo validationError = ErrorInfo.builder()
                    .message(MessageConstants.PERSISTENCE_ERROR)
                    .code(CODE_ERROR_PERSISTENCE_ERROR).build();
            throw new PersistenceException(validationError);
        }
    }

    @Override
    public void addProfile(DriverProfile driverProfile) {
        driverProfileRepository.save(driverProfile);
    }

    @Override
    public void validateProfile(DriverProfileUpdate driverProfileUpdate) throws InvalidInputException {
        ErrorInfo validationError = validator.validateDriverProfile(driverProfileUpdate);
        if( !validationError.getDetails().isEmpty() ) {
            validationError.setMessage(MessageConstants.INVALID_DRIVER_INFO);
            validationError.getDetails().forEach(errorDetails -> log.error(errorDetails.getMessage()));
            throw new InvalidInputException(validationError);
        }
    }

}
