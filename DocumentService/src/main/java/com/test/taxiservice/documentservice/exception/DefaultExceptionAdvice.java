package com.test.taxiservice.documentservice.exception;


import com.test.taxiservice.documentservice.common.HeaderInfo;
import com.test.taxiservice.taxiservicecommon.common.ResponseConstants;
import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;
import com.test.taxiservice.taxiservicecommon.exception.ErrorUtils;
import com.test.taxiservice.taxiservicecommon.exception.InvalidInputException;
import com.test.taxiservice.taxiservicecommon.exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

/**
 * Generic Exception handler for the entire application
 *
 * If any of the exception is not handled in the highest priority Exception handler, the Default Exception handler would
 * catch it and show a formatted response
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionAdvice {

    @Autowired
    private Provider<HeaderInfo> headerInfo;

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorInfo> invalidInputException(final InvalidInputException exception, final HttpServletRequest request) {
        log.error("Error processing request : handleException : NotImplementedException : {}", exception.getMessage());

        ErrorInfo errorResponse = exception.getValidationErrorInfo();
        errorResponse.setTarget(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(headerInfo.get().getResponseHeaders())
                .body(errorResponse);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorInfo> persistenceException(final PersistenceException exception, final HttpServletRequest request) {
        log.error("Error processing request : handleException : NotImplementedException : {}", exception.getMessage());

        ErrorInfo errorResponse = exception.getValidationErrorInfo();
        errorResponse.setTarget(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headerInfo.get().getResponseHeaders())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> genericException(final Exception exception, final HttpServletRequest request) {
        log.error("Error processing request : handleException : {} : {}",
                exception.getClass().getSimpleName(), exception.getMessage());

        ErrorInfo errorResponse = ErrorUtils.getError(ResponseConstants.CODE_ERROR_INTERNALSERVERERROR,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headerInfo.get().getResponseHeaders())
                .body(errorResponse);
    }
}
