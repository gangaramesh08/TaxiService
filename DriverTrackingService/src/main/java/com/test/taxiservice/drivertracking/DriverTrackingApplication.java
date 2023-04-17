package com.test.taxiservice.drivertracking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.test.taxiservice.drivertracking.common.Constants.BASE_PACKAGE;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {BASE_PACKAGE})
@EntityScan(basePackages = {BASE_PACKAGE})
@EnableJpaRepositories(basePackages = {BASE_PACKAGE})
public class DriverTrackingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DriverTrackingApplication.class, args);
    }

}
