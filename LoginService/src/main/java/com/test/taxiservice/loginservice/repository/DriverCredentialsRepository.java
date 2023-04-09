package com.test.taxiservice.loginservice.repository;

import com.test.taxiservice.loginservice.model.DriverCredentials;
import com.test.taxiservice.loginservice.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface DriverCredentialsRepository extends JpaRepository<DriverCredentials, BigInteger> {

    @Query(value = "Select mobile_number from driver_credentials dC where dC.mobile_number = ?1", nativeQuery = true)
    String getByMobileNumber(String mobileNumber);


    @Query(value = "Select * from driver_credentials dI where dI.mobile_number = ?1 and dI.password = ?2", nativeQuery = true)
    DriverProfile getByMobileNumberAndPassword(String mobileNumber, String password);
}