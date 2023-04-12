package com.test.taxiservice.taxiservicecommon.model.loginservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DriverCredentials")
public class DriverCredentials {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "driverId")

    private BigInteger driverId;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "modifiedAt")
    private Date modifiedAt;


}
