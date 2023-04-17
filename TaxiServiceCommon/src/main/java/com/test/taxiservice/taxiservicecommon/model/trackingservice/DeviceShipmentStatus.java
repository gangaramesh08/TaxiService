package com.test.taxiservice.taxiservicecommon.model.trackingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DeviceShipmentStatus")
public class DeviceShipmentStatus {
    @Id
    @Column(name = "shipmentId")
    private BigInteger shipmentId;

    @Column(name = "deviceId")
    private BigInteger deviceStatus;

    @Column(name = "driverId")
    private BigInteger driverId;

    @Column(name = "status")
    private String status;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "modifiedAt")
    private Date modifiedAt;
}
