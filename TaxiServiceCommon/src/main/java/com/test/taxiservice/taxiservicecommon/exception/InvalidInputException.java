package com.test.taxiservice.taxiservicecommon.exception;

import lombok.Data;

@Data
public class InvalidInputException extends Exception{

    private static final long serialVersionUID = 1L;

    private final ErrorInfo validationErrorInfo;

    public InvalidInputException(final ErrorInfo validationErrorInfo) {
        super(validationErrorInfo.getMessage());
        this.validationErrorInfo = validationErrorInfo;
    }


}
