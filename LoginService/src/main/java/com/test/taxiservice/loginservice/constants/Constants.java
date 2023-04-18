package com.test.taxiservice.loginservice.constants;

public class Constants {

    private Constants(){


    }
    public static final String BASE_PACKAGE = "com.test.taxiservice";
    public static final String NAME_REGEX = "^[a-zA-Z\\\\s]+";
    public static final String MOBILENUMBER_REGEX = "^[1-9][0-9]{9}$";
    public static final String EMAIL_REGEX =  "^(.+)@(\\S+)$";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String AGE = "age";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";

    public static final String LOGINSERVICE_API_URL = "/driver/loginservice";
    public static final String HEALTH_CHECK_URL = "/healthcheck";
    public static final String LOGIN_API_URL = "/login";
    public static final String SIGNUP_API_URL = "/signup";
    public static final String VALIDATE_OTP_API_URL = "/validateotp";
    public static final String REGENERATE_OTP_API_URL = "/regenerateotp";
    public static final String PROFILE_UPDATE_API_URL = "/profile/update";

    public static final long EXPIRY_DURATION = 24 * 60 * 60 * 1000;

    public static final String BEARER = "Bearer ";
    public static final String SUCCESS = "SUCCESS ";
}
