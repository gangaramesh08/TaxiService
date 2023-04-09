package com.test.taxiservice.loginservice.exceptions;

import lombok.Data;

@Data
public class PersistenceException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorInfo validationErrorInfo;

    public PersistenceException(final ErrorInfo validationErrorInfo) {
        super(validationErrorInfo.getMessage());
        this.validationErrorInfo = validationErrorInfo;
    }

}
