package com.lirlo.baseplat.core.config;

import org.springframework.stereotype.Component;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;

@Component
public class NotLoginCheckConfig implements SwaggerAuthorizeConfig{
    @Override
    public SecurityScheme securityScheme() {
        return new ApiKey("NotLoginCheck", "NotLoginCheck", "header");
    }

    @Override
    public SecurityReference securityReference() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{new AuthorizationScope("all", "all")};
        return new SecurityReference("NotLoginCheck", authorizationScopes);
    }
}
