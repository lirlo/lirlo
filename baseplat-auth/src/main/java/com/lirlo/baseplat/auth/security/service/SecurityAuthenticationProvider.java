package com.lirlo.baseplat.auth.security.service;//package com.lirlo.baseplat.core.security.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
//@Service
//public class SecurityAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String userName = authentication.getName();// 这个获取表单输入中的用户名
//        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码
//        String encPwd = passwordEncoder.encode(password);
//        System.out.println("encPwd = [" + encPwd + "]");
//        /** 判断用户是否存在 */
//        UserDetails user = userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法
//        if (user == null) {
//            throw new BadCredentialsException("用户不存在");
//        }
//
//        /** 判断密码是否正确 */
////        if (!passwordEncoder.matches(password,user.getPassword())) {
////            throw new BadCredentialsException("密码不正确");
////        }
//        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
////        session.setAttribute("user",user);
//        return new UsernamePasswordAuthenticationToken(user, password, authorities);// 构建返回的用户登录成功的token
//    }
//
//
//    /**
//     * 执行支持判断
//     *
//     * @param authentication
//     * @return
//     */
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;// 返回 true ，表示支持执行
//    }
//}
