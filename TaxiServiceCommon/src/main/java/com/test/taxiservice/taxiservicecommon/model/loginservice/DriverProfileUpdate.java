package com.test.taxiservice.taxiservicecommon.model.loginservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverProfileUpdate {
    private String mobileNumber;
    private String firstName;

    private String lastName;

    private int age;

    private Date modifiedAt;

    private String email;
}
