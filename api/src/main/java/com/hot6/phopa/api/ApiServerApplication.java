package com.hot6.phopa.api;

import com.hot6.phopa.BasePackageLocation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackageClasses = BasePackageLocation.class)
@ComponentScan(basePackageClasses = BasePackageLocation.class)
@EntityScan(basePackageClasses = BasePackageLocation.class)
@EnableJpaRepositories(basePackageClasses = BasePackageLocation.class)
public class ApiServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }
}
