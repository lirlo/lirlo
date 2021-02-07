package com.lirlo.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties({RocketConfig.class})
@ConfigurationProperties(prefix = "rocket")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RocketConfig {


    private String producerGroup;

    private String namesrvAddr;

    private String instanceName;


}
