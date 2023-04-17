package com.test.taxiservice.loginservice.repository;
import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface DriverCredentialsRepository extends JpaRepository<DriverCredentials, BigInteger> {

    @Query(value = "Select mobile_number from driver_credentials dC where dC.mobile_number = ?1", nativeQuery = true)
    String getByMobileNumber(String mobileNumber);


    @Query(value = "Select * from driver_credentials dI where dI.mobile_number = ?1 and dI.password = ?2", nativeQuery = true)
    DriverCredentials getByMobileNumberAndPassword(String mobileNumber, String password);

    @Query(value = "Select * from driver_credentials dC where dC.mobile_number = ?1", nativeQuery = true)

    DriverCredentialsRepository findByMobileNumber(String mobileNumber);
}
