package com.test.taxiservice.loginservice.repository;

import com.test.taxiservice.loginservice.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


@Repository
public interface DriverProfileRepository extends JpaRepository<DriverProfile, BigInteger> {
}
