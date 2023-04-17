package com.test.taxiservice.documentservice.repository;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverBackgroundVerification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface DriverBackgroundStatusRepository extends CrudRepository<DriverBackgroundVerification, BigInteger> {

    @Query(value = "Select * from driver_background_verification where driver_id=?1", nativeQuery = true)
    DriverBackgroundVerification findByDriverId(BigInteger driverId);
}
