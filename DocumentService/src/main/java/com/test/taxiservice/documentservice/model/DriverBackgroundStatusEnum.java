package com.test.taxiservice.documentservice.model;

public enum DriverBackgroundStatusEnum {
    INPROGRESS("INPROGRESS"),
    FAILED("FAILED"),
    COMPLETED("COMPLETED");

    private String value;

    DriverBackgroundStatusEnum(String valule) {
        this.value = valule;
    }

    public String getValue() {
        return this.value;
    }
}
