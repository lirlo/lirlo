package com.lirlo.core.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConfigurationProperties( prefix = "swagger-config" )
@EnableConfigurationProperties({LirloProperties.class})
@Data
public class SwaggerConfig {

    @Autowired
    LirloProperties lirloProperties;

    @Resource
    ConfigurableListableBeanFactory beanFactory;

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


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).groupName("首页")
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                .enable(lirloProperties.swaggerConfig.enableSwagger)
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage("com.lirlo"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    public Docket createDocket(DocketProperties docketProperties){
        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                .enable(lirloProperties.swaggerConfig.enableSwagger).groupName(docketProperties.getGroupName())
                .select()
                //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
                .apis(RequestHandlerSelectors.basePackage(docketProperties.getApiBasePackage()))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
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
}
