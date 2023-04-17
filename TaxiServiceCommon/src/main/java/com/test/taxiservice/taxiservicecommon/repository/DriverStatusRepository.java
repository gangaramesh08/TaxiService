package com.test.taxiservice.taxiservicecommon.repository;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface DriverStatusRepository extends CrudRepository<DriverStatus, BigInteger> {

    @Query(value = "Select ds.status from driver_status ds where driver_id = ?1", nativeQuery = true)
    String findDriverStatusById(BigInteger driverId);

    @Query(value = "Select * from driver_status ds where driver_id = ?1", nativeQuery = true)

    DriverStatus findByDriverId(BigInteger driverId);

}
