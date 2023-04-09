package com.test.taxiservice.loginservice.repository;

import com.test.taxiservice.loginservice.model.DriverInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


@Repository
public interface DriverInfoRepository extends JpaRepository<DriverInfo, BigInteger> {

    @Query(value = "Select phoneNumber from DriverInfo dI where dI.phoneNumber = ?1", nativeQuery = true)
    String getByPhoneNumber(String phoneNumber);

}
