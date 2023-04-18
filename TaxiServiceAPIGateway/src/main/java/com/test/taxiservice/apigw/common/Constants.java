package com.test.taxiservice.apigw.common;

public class Constants {

    private Constants(){


    }

    public static final String DRIVER_MOBILE_NUMBER = "mobileNumber";
    public static final String DRIVER_OTP = "otp";
    public static final String APIGW = "/apigw";
    public static final String LOGINSERVICE_LOGIN_URL = "/driver/loginservice/login";
    public static final String LOGINSERVICE_SIGNUP_URL = "/driver/loginservice/signup";
    public static final String LOGINSERVICE_PROFILE_UPDATE_URL = "/driver/loginservice/profile/update";
    public static final String LOGINSERVICE_VALIDATE_OTP_URL = "/driver/loginservice/validateotp";

    public static final String LOGINSERVICE_REGENERATE_OTP_URL = "/driver/loginservice/regenerateotp";
    public static final String HEALTH_CHECK = "/healthcheck";


}
