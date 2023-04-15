package com.test.taxiservice.documentservice.model;

public enum DocumentStorageType {
    LOCAL("LOCAL"),
    S3("S3");

    private String value;

    DocumentStorageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
