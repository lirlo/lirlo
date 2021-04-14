package com.lirlo.baseplat.core.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConfigurationProperties( prefix = "swagger-config" )
@EnableConfigurationProperties({LirloProperties.class})
@ConditionalOnProperty(
        value = {"lirlo.swagger-config.enable-swagger"},
        havingValue = "true",
        matchIfMissing = true
)
@Data
public class SwaggerConfig implements ApplicationContextAware {

    @Autowired
    LirloProperties lirloProperties;

    @Resource
    ConfigurableListableBeanFactory beanFactory;

    @Autowired
    private List<SwaggerAuthorizeConfig> swaggerAuthorizeConfigList;

    /**
     * 是否开启swagger
     */
    private boolean enableSwagger;

    /**
     * docket
     */
    private List<DocketProperties> dockets;

    /**
     * 版本号
     */
    private String version;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    private DefaultListableBeanFactory defaultListableBeanFactory;
    private ApplicationContext applicationContext;


    public Docket createDocket(DocketProperties docketProperties){
        Docket docket = (new Docket(DocumentationType.SWAGGER_2))
                .useDefaultResponseMessages(false)
                .apiInfo(this.apiInfo())
                .groupName(docketProperties.getGroupName()+ ":" + docketProperties.getVersion())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).build()
                .securityContexts(Collections.singletonList(this.getSecurityContext(swaggerAuthorizeConfigList)))
                .securitySchemes(this.getSecuritySchemeList(swaggerAuthorizeConfigList));
        return StringUtils.hasText(docketProperties.getApiBasePackage()) ?
                docket.select().apis(RequestHandlerSelectors.basePackage(docketProperties.getApiBasePackage())).build() : docket;
    }

    @PostConstruct
    public void init() {
//        List<DocketProperties> list = new ArrayList<>();
        List<DocketProperties> list = lirloProperties.swaggerConfig.dockets;
        list.stream().forEach(docketProperties1 -> {
            this.beanFactory.registerSingleton(docketProperties1.getBeanName(),this.createDocket(docketProperties1));
        });


    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title(lirloProperties.swaggerConfig.title)
                //文档描述
                .description(lirloProperties.swaggerConfig.description)
                //服务条款URL
                .termsOfServiceUrl("http://127.0.0.1:8080/")
                //联系信息
                .contact(new Contact("lirlo","https://blog.csdn.net/Lirlolqw","llqwan0627@126.com"))
                //版本号
                .version(lirloProperties.swaggerConfig.version)
                .build();
    }

    private SecurityContext getSecurityContext(List<SwaggerAuthorizeConfig> swaggerAuthorizeConfigList) {
        List<SecurityReference> securityReferenceList = (List)swaggerAuthorizeConfigList.stream().map((x) -> {
            return x.securityReference();
        }).collect(Collectors.toList());
        return SecurityContext.builder().securityReferences(securityReferenceList).build();
    }

    private List<SecurityScheme> getSecuritySchemeList(List<SwaggerAuthorizeConfig> swaggerAuthorizeConfigList) {
        return (List)swaggerAuthorizeConfigList.stream().map((x) -> {
            return x.securityScheme();
        }).collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            this.beanFactory = ((AbstractRefreshableApplicationContext)applicationContext).getBeanFactory();
        } else {
            this.beanFactory = ((GenericApplicationContext)applicationContext).getBeanFactory();
            this.defaultListableBeanFactory = ((GenericApplicationContext)applicationContext).getDefaultListableBeanFactory();
        }

    }
}
