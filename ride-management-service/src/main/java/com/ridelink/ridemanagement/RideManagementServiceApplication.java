package com.ridelink.ridemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class RideManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideManagementServiceApplication.class, args);
    }
}