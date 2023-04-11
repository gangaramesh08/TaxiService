package com.test.taxiservice.taxiservicecommon.model.loginservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInfo {

    private String mobileNumber;

    private String password;

    private String firstName;

    private String lastName;
}
