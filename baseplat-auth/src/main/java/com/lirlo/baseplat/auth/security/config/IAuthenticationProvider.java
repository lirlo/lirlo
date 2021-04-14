package com.lirlo.baseplat.auth.security.config;

import com.lirlo.baseplat.auth.security.model.JwtUser;
import com.lirlo.baseplat.auth.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class IAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String presentedPassword = (String)authentication.getCredentials();
        JwtUser userDeatils = (JwtUser) userDetailsService.loadUserByUsername(username);
        // 根据用户名获取用户信息
        String passwordEncrypt = userDeatils.password;
        if (StringUtils.isEmpty(userDeatils)) {
            throw new BadCredentialsException("用户名不存在");
        } else {
            if (authentication.getCredentials() == null) {
                throw new BadCredentialsException("登录名或密码错误");
            }
            if (!new BCryptPasswordEncoder().matches(presentedPassword,passwordEncrypt)) {
                throw new BadCredentialsException("登录名或密码错误");
            }
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDeatils, authentication.getCredentials(), userDeatils.getAuthorities());
            result.setDetails(authentication.getDetails());
            return result;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
