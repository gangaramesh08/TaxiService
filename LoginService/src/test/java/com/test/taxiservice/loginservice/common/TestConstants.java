package com.test.taxiservice.loginservice.common;

import java.math.BigInteger;

public class TestConstants {

    public static final String VALID_MOBILE_NUMBER = "1122334455";
    public static final BigInteger VALID_DRIVER_ID = new BigInteger("1");
    public static final String VALID_PASSWORD = "password";
    public static final String INVALID_PASSWORD = "not_password";
    public static final String INVALID_MOBILE_NUMBER = "11223344";
    public static final Integer VALID_OTP = 654327;
    public static final Integer INVALID_OTP = 123456;
    public static final String VALID_TOKEN = "validToken";
    public static final String INVALID_TOKEN = "invalidToken";
    public static final String SUCCESS = "SUCCESS";

    public static final String VALID_SIGNUP_REQUEST = "signup_success.json";
    public static final String INVALID_SIGNUP_REQUEST = "signup_invalid.json";
    public static final String VALID_PROFILE_UPDATE_REQUEST = "profile_update_valid.json";
    public static final String INVALID_PROFILE_UPDATE_REQUEST = "profile_update_invalid_address1.json";
}
