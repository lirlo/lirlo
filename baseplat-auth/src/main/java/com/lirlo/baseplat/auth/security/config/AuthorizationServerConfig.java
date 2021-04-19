package com.lirlo.baseplat.auth.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.lirlo.baseplat.auth.security.service.OauthClientDetailsServiceImpl;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.Arrays;
/**
 * @author lirlo
 * @since 2021/04/14
 * @description 认证服务器配置
 * @version 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private OauthClientDetailsServiceImpl oauthClientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private DruidDataSource dataSource;

    /**
     * signingKey
     */
    @Value("${lirlo.security.jwt.signingKey}")
    private String signingKey;

    /**
     * 已授权客户端存储记录
     * @return
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * access_token 采用redis缓存，Redis连接池采用Jedis框架
     * 当然也可以采用Jdbc实现用mysql存储
     * @return
     */
    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(new RedissonConnectionFactory());
    }

    /**
     * 授权码code的存储-mysql中
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        JdbcAuthorizationCodeServices service = new JdbcAuthorizationCodeServices(dataSource);
        return service;
    }
    // 配置Oauth2客户端，存储到数据库中
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauthClientDetailsService);
    }

    /**
     * 配置oauth2端点信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)
                .approvalStore(approvalStore())
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore()).authorizationCodeServices(authorizationCodeServices())
                .accessTokenConverter(accessTokenConverter());

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        endpoints.tokenEnhancer(tokenEnhancerChain);

    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();  //主要是让/oauth/token支持client_id以及client_secret作登录认证
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }


}
