package com.test.taxiservice.taxiservicecommon.repository;

import com.test.taxiservice.taxiservicecommon.model.DriverAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface DriverAccessTokenRepository extends CrudRepository<DriverAccessToken, String> {
}
