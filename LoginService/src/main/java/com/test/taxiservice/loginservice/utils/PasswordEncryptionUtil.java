package com.test.taxiservice.loginservice.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptionUtil {
    private static final BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String getEncryptedPassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }
}
