package com.app.quantitymeasurementapp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.app.quantitymeasurementapp")
@EnableJpaRepositories(basePackages = "com.app.quantitymeasurementapp.repository")
@EntityScan(basePackages = "com.app.quantitymeasurementapp.model")
public class QuantityMeasurementApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}