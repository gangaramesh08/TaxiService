package com.test.taxiservice.loginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static com.test.taxiservice.loginservice.constants.Constants.BASE_PACKAGE;

@SpringBootApplication
@ComponentScan(BASE_PACKAGE)
public class LoginServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(LoginServiceApplication.class, args);
    }
}