package com.test.taxiservice.drivertracking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalShipmentRequest {

    private BigInteger shipmentId;
    private String status;
}
