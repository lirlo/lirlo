package com.lirlo.baseplat.cas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lirlo.cas")
@Data
public class CasProperties {

    private static final String separator = ",";

    private String validateFilters;

    private String signOutFilters;

    private String authFilters;

    private String assertionFilters;

    private String requestWrapperFilters;

    private String casServerUrlPrefix;

    private String casServerLoginUrl;

    private String serverName;

    private boolean useSession = true;

    private boolean redirectAfterValidation = true;

}