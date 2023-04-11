package com.test.taxiservice.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })

public class ApiGwApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ApiGwApplication.class, args);
    }

}