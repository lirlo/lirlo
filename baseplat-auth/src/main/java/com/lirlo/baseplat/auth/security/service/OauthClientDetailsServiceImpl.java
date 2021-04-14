package com.lirlo.baseplat.auth.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirlo.baseplat.auth.security.mapper.OauthClientDetailsMapper;
import com.lirlo.baseplat.auth.security.model.OauthClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * @author qiwen.li
 * @since 2021/04/14
 * @description oauth2 client客户端信息查询
 * @version 1.0.0
 */
@Slf4j
@Configuration
public class OauthClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;
    /**
     * 根据id client_id获取客户端信息
     * @param s
     * @return
     * @throws ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        // 根据Id查询
        QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OauthClientDetails::getClient_id,s);
        OauthClientDetails oauthClientDetails = oauthClientDetailsMapper.selectOne(queryWrapper);
        try {
            return translateClient(oauthClientDetails);
        } catch (Exception e) {
            log.error("===> {}", e);
            throw new ClientRegistrationException("无效client");
        }
    }

    public ClientDetails translateClient(OauthClientDetails details) {
        BaseClientDetails clientDetails = new BaseClientDetails(details.getClient_id(), details.getResource_ids(), details.getScope(),
                details.getAuthorized_grant_types(), details.getAuthorities(), details.getWeb_server_redirect_uri());
        clientDetails.setClientSecret(new BCryptPasswordEncoder().encode(details.getClient_secret()));
        clientDetails.setScope(StringUtils.commaDelimitedListToSet(details.getScope()));
        clientDetails.setAutoApproveScopes(new ArrayList<String>());
        clientDetails.setRefreshTokenValiditySeconds(details.getRefresh_token_validity());
        clientDetails.setAccessTokenValiditySeconds(details.getAccess_token_validity());
        return clientDetails;
    }
}
