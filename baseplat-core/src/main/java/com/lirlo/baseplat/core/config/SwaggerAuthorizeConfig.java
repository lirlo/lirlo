package com.lirlo.baseplat.core.config;

import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;

public interface SwaggerAuthorizeConfig {

    SecurityScheme securityScheme();

    SecurityReference securityReference();
}
