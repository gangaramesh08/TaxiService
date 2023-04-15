package com.test.taxiservice.taxiservicecommon.model.documentservice;

public enum DriverStatusEnum {
    NEW("NEW"),
    DOCUMENT_UPLOAD_COMPLETE("DOCUMENT_UPLOAD_COMPLETE"),
    BACKGROUND_VERIFICATION_INPROGRESS("BACKGROUND_VERIFICATION_INPROGRESS"),
    BACKGROUND_VERIFICATION_COMPLETED("BACKGROUND_VERIFICATION_COMPLETED"),
    BACKGROUND_VERIFICATION_FAILED("BACKGROUND_VERIFICATION_FAILED"),
    ACTIVE("ACTIVE");

    private String value;

    DriverStatusEnum(String value) {
        this.value=value;
    }

    public String getValue() {
        return this.value;
    }
}
