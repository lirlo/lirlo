package com.lirlo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.lirlo")
@EnableConfigurationProperties
public class LirloApplication {

	public static void main(String[] args) {
		SpringApplication.run(LirloApplication.class, args);
	}

}
