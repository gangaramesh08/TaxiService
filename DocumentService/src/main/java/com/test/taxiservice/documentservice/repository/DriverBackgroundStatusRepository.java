package com.test.taxiservice.documentservice.repository;

import com.test.taxiservice.taxiservicecommon.model.documentservice.DriverBackgroundVerification;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface DriverBackgroundStatusRepository extends CrudRepository<DriverBackgroundVerification, BigInteger> {
}
