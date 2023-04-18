package com.test.taxiservice.taxiservicecommon.model.loginservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInfo implements Serializable {

    private String mobileNumber;

    private String password;

    private String firstName;

    private String lastName;
}
