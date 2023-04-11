package com.test.taxiservice.taxiservicecommon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigInteger;

@RedisHash(value="DriverMobileId")
@AllArgsConstructor
@Data
public class DriverMobileId {

    @Id
    private String mobileNumber;
    private BigInteger driverId;
}
