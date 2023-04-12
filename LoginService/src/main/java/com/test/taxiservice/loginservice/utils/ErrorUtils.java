package com.test.taxiservice.loginservice.utils;

import com.test.taxiservice.taxiservicecommon.exception.ErrorInfo;


public class ErrorUtils {

    private ErrorUtils() {}

    /**
	 * Helper for the Error message. As per the NorthStar KPI, 'target' attribute is optional
	 * 
	 * @param errorCode
	 * @param message
	 * @param url
	 * @return
	 */
	public static ErrorInfo getError(final String errorCode, final String message, final String url) {
		ErrorInfo error = new ErrorInfo();
		error.setCode(errorCode);
		error.setMessage(message);
		error.setTarget(url);
		return error;
	}
    
}
