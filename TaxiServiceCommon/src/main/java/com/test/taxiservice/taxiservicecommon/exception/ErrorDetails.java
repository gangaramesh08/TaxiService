package com.test.taxiservice.taxiservicecommon.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Details about individual error
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private String message;

  private String code;

}

