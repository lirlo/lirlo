package com.lirlo.baseplat.auth.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lirlo.baseplat.auth.security.mapper.UserMapper;
import com.lirlo.baseplat.auth.security.model.JwtUser;
import com.lirlo.baseplat.auth.security.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author qiwen.li
 * @since 2021/04/14
 * @description UserDetailsService实现类
 * @version 1.0.0
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${security.jwt.header}")
    private String tokenHeader;

    /**
     * 通过用户名加载用户信息
     * @param s 用户名username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        QueryWrapper<JwtUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(JwtUser::getUsername,s);
        JwtUser user = userMapper.selectOne(queryWrapper);
        System.out.println(user);

        String username = user.username;
        String password = user.password;

        Collection<GrantedAuthority> authority = getAuthorities();
        return new User(username,password, authority);
//        return new User("test",new BCryptPasswordEncoder().encode("111111"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


    /**
     * 获取用户角色
     * // TODO: 2021/4/14 需重写
     * @return
     */
    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * 通过token获取用户信息
     * @param request
     * @return
     */
    public JwtUser getAuthenticatedUser(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) loadUserByUsername(username);
        return user;
    }

    /**
     * 生成token
     * @param userDetails
     * @return
     */
    public String createAuthenticationToken(UserDetails userDetails){
        return jwtTokenUtil.generateToken(userDetails);
    }

}