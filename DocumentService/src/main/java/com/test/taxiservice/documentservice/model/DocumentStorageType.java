package com.test.taxiservice.documentservice.model;

public enum DocumentStorageType {
    LOCAL("LOCAL"),
    AWS("AWS");

    private String value;

    DocumentStorageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
