package com.test.taxiservice.taxiservicecommon.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A value type representing a technical error which may contain detailed descriptions about it.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String code;

  private String message;

  private String target;

  private List<ErrorDetails> details = new ArrayList<>();

  public ErrorInfo addDetailsItem(ErrorDetails detailsItem) {
    if (this.details == null) {
      this.details = new ArrayList<>();
    }
    this.details.add(detailsItem);
    return this;
  }

  public ErrorInfo target(String target) {
    this.target = target;
    return this;
  }

  public ErrorInfo code(String code) {
    this.code = code;
    return this;
  }

  public ErrorInfo message(String message) {
    this.message = message;
    return this;
  }

}

