package com.test.taxiservice.taxiservicecommon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value="DriverAccessToken")
@AllArgsConstructor
@Data
public class DriverAccessToken implements Serializable {
    @Id
    private String mobileNumber;
    private String token;

    private long expiry;
}


