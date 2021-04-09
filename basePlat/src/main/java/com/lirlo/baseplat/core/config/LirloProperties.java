package com.lirlo.baseplat.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(LirloProperties.class)
@Configuration
@ConfigurationProperties( prefix = "lirlo" )
@Data
public class LirloProperties {

    @NestedConfigurationProperty
    SwaggerConfig swaggerConfig;
}
