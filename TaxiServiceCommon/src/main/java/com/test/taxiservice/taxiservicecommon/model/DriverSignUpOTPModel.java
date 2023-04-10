package com.test.taxiservice.taxiservicecommon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value="DriverSignUpOTP", timeToLive = 60L)
@AllArgsConstructor
@Data
public class DriverSignUpOTPModel implements Serializable {
    @Id
    private String mobileNumber;
    private Integer otp;

}
