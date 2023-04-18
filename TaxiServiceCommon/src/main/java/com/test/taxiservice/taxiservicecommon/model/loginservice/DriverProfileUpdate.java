package com.test.taxiservice.taxiservicecommon.model.loginservice;

import com.test.taxiservice.taxiservicecommon.model.trackingservice.DriverAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverProfileUpdate {
    private String mobileNumber;
    private String firstName;

    private String lastName;

    private int age;

    private Date modifiedAt;

    private String email;

    private BigInteger vehicleId;

    private List<DriverAddress> addressList;
}
