package com.test.taxiservice.loginservice.utils;

import java.util.Random;

public class OtpGeneratorUtil {

    public static Integer generateOtp(){
        Random rnd = new Random();
        return  rnd.nextInt(999999);
    }
}
