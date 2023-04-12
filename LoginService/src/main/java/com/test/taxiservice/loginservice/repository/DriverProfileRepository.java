package com.test.taxiservice.loginservice.repository;

import com.test.taxiservice.taxiservicecommon.model.loginservice.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


@Repository
public interface DriverProfileRepository extends JpaRepository<DriverProfile, BigInteger> {

    @Query(value = "Select * from driver_profile dp where dp.driver_id = ?1", nativeQuery = true)
    DriverProfile findByDriverId(BigInteger driverId);
}
