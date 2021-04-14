package com.lirlo.baseplat.auth.security.config;

import com.lirlo.baseplat.core.config.SwaggerAuthorizeConfig;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;

/**
 * @author qiwen.li
 * @since 2021/04/14
 * @description Oauth2SwaggerAuthorize配置类
 * @version 1.0.0
 */
@Component
public class Oauth2SwaggerAuthorizeConfig implements SwaggerAuthorizeConfig {


    @Override
    public SecurityScheme securityScheme() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    @Override
    public SecurityReference securityReference() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new SecurityReference("Authorization", authorizationScopes);
    }
}
