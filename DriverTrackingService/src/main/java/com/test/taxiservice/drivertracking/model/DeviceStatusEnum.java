package com.test.taxiservice.drivertracking.model;

public enum DeviceStatusEnum {
    INPROGRESS("INPROGRESS"),
    COMPLETED("COMPLETED");

    private String value;

    DeviceStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
