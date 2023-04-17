package com.test.taxiservice.taxiservicecommon.repository;

import com.test.taxiservice.taxiservicecommon.model.trackingservice.DriverAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface DriverAddressRepository extends CrudRepository<DriverAddress, BigInteger> {
    @Query(value = "Select * from driver_address where driver_id=?1", nativeQuery = true)
    List<DriverAddress> findByDriverId(BigInteger driverId);
}
