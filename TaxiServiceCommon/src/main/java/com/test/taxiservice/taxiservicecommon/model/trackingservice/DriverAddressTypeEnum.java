package com.test.taxiservice.taxiservicecommon.model.trackingservice;

public enum DriverAddressTypeEnum {
    PERMANENT("PERMANENT"),
    CURRENT("CURRENT");

    private String value;

    DriverAddressTypeEnum(String value) {
        this.value= value;
    }

    public String getValue(){
        return this.value;
    }
}
