package com.test.taxiservice.apigw.common;

public class Constants {

    public static final String DRIVER_MOBILE_NUMBER = "mobileNumber";
    public static final String DRIVER_OTP = "otp";
    public static class ApiGWUrl{
        public static final String APIGW = "/apigw";
        public static final String LOGINSERVICE_LOGIN_URL = "/loginservice/login";
        public static final String LOGINSERVICE_SIGNUP_URL = "/loginservice/signup";
        public static final String LOGINSERVICE_PROFILE_UPDATE_URL = "/loginservice/profile/update";
        public static final String LOGINSERVICE_VALIDATE_OTP_URL = "/loginservice/validateOTP";

        public static final String LOGINSERVICE_REGENERATE_OTP_URL = "/loginservice/regenerateOTP";
        public static final String HEALTH_CHECK = "/healthcheck";

    }
}
