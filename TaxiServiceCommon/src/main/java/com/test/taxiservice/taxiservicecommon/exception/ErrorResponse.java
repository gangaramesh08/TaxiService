package com.test.taxiservice.taxiservicecommon.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * A service response object that contains technical error data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private ErrorInfo error;

  public ErrorResponse error(ErrorInfo error) {
    this.error = error;
    return this;
  }
}

