package com.test.taxiservice.loginservice.model;

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
@Entity(name = "DriverInfo")
public class DriverInfo {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "driverId")
    BigInteger driverId;
    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @Column(name = "age")
    int age;

    @Column(name = "phoneNumber")
    String phoneNumber;

    @Column(name = "password")
    String password;

    @Column(name = "createdAt")
    Date createdAt;

    @Column(name = "modifiedAt")
    Date modifiedAt;

    String email;

}
