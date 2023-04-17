package com.test.taxiservice.taxiservicecommon.model.trackingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DriverAddress")
public class DriverAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private BigInteger addressId;

    @Column(name = "driverId")
    private BigInteger driverId;

    @Column(name = "houseNumber")
    private String houseNumber;

    @Column(name = "streetName")
    private String streetName;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "type")
    private String type;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "modifiedAt")
    private Date modifiedAt;
}
