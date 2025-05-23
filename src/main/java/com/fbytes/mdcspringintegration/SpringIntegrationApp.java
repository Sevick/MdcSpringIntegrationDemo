package com.fbytes.mdcspringintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SpringIntegrationApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationApp.class, args);
    }
}