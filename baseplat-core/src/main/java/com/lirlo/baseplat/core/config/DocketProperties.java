package com.lirlo.baseplat.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "dockets")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DocketProperties {


    private String groupName;

    private String apiBasePackage;

    private String beanName;

    private String version;


}
