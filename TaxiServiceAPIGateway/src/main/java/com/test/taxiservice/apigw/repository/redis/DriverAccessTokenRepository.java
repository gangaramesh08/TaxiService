package com.test.taxiservice.apigw.repository.redis;

import com.test.taxiservice.taxiservicecommon.model.DriverAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface DriverAccessTokenRepository extends CrudRepository<DriverAccessToken, String> {
}
