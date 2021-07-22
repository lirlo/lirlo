package com.lirlo.baseplat.cas.config;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CasConfig {

    // 是否启用CAS
    private static boolean casEnabled  = true;

    @Autowired
    private CasProperties autoConfig;

    /**
         * SingleSignOutFilter
         * 必须放在最前面
         */
    @Bean
    public FilterRegistrationBean<SingleSignOutFilter> filterSingleRegistration() {
        FilterRegistrationBean<SingleSignOutFilter> registration = new FilterRegistrationBean<SingleSignOutFilter>();
        registration.setFilter(new SingleSignOutFilter());
        Map<String,String> initParameters = new HashMap<String,String>();
        initParameters.put("casServerUrlPrefix",autoConfig.getCasServerUrlPrefix() );
        registration.setInitParameters(initParameters);
        //set mapping url
        registration.addUrlPatterns("/*");
        //set loading sequence
        registration.setOrder(1);
        return registration;
    }

    /**
         * 添加监听器
         * @return
         */
    @Bean
    public ServletListenerRegistrationBean<EventListener> singleSignOutListenerRegistration(){ 
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<EventListener>();
        registrationBean.setListener(new SingleSignOutHttpSessionListener()); 
        registrationBean.setOrder(1); 
        return registrationBean; 
    } 
    /**
     *  授权过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> filterAuthenticationRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<AuthenticationFilter>();
        registration.setFilter(new AuthenticationFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map<String,String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerLoginUrl", autoConfig.getCasServerLoginUrl());
        initParameters.put("serverName", autoConfig.getServerName());
        registration.setInitParameters(initParameters);
        //设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * CAS Validation Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> filterValidationRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
        //set mapping url
        registration.addUrlPatterns("/*");
        Map<String,String> initParameters = new HashMap<String, String>();
        initParameters.put("casServerUrlPrefix", autoConfig.getCasServerUrlPrefix());
        initParameters.put("serverName", autoConfig.getServerName());
        initParameters.put("useSession", "true");
        registration.setInitParameters(initParameters);
        registration.setOrder(1);
        return registration;
    }

    /**
     * wraper过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<HttpServletRequestWrapperFilter> filterWrapperRegistration() {
        FilterRegistrationBean<HttpServletRequestWrapperFilter> registration = new FilterRegistrationBean<HttpServletRequestWrapperFilter>();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

}