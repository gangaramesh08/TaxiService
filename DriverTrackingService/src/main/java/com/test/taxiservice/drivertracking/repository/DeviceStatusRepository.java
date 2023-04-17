package com.test.taxiservice.drivertracking.repository;

import com.test.taxiservice.taxiservicecommon.model.trackingservice.DeviceShipmentStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface DeviceStatusRepository extends CrudRepository<DeviceShipmentStatus, BigInteger> {

    @Query(value = "Select * from device_status where driver_id=?1", nativeQuery = true)
    DeviceShipmentStatus findByDriverId(BigInteger driverId);
}
