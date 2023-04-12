package com.test.taxiservice.taxiservicecommon.model.documentservice;

public enum DocumentType {

    PAN_CARD ("PAN_CARD"),
    AADHAR_CARD("AADHAR_CARD"),
    DRIVING_LICENCE("DRIVING_LICENCE");

    private String value;

    DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
