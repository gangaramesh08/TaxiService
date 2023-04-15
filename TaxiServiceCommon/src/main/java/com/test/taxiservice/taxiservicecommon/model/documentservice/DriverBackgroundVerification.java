package com.test.taxiservice.taxiservicecommon.model.documentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DriverBackgroundVerification")
public class DriverBackgroundVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verificationId")
    private BigInteger verificationId;

    @Column(name = "driverId")
    private BigInteger driverId;

    @Column(name = "status")
    private String status;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "modifiedAt")
    private Date modifiedAt;
}
