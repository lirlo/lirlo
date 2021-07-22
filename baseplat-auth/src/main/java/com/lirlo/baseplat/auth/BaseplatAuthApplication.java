package com.lirlo.baseplat.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.lirlo.baseplat")
@EnableConfigurationProperties
public class BaseplatAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseplatAuthApplication.class, args);
    }

}
