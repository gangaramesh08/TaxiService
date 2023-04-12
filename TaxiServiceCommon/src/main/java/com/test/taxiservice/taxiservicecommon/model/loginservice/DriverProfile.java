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
@Entity(name = "DriverProfile")
public class DriverProfile {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "profileId")
    private BigInteger profileId;

    @Column(name = "driverId")

    private BigInteger driverId;
    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "modifiedAt")
    private Date modifiedAt;

    private String email;

}
