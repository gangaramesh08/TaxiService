package com.test.taxiservice.taxiservicecommon.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface IDeviceService {
    BigInteger getNewDevice();
}
