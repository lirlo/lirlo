package com.lirlo.baseplat.auth.security.controller;

import com.lirlo.baseplat.auth.security.model.ResultInfo;
import com.lirlo.baseplat.auth.security.model.JwtUser;
import com.lirlo.baseplat.auth.security.service.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "权限管理")
@RestController
public class SecurityController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/createAuthenticationToken")
    @ApiOperation(
            value = "获取token",
            httpMethod = "POST"
    )
    @ApiImplicitParam(value = "JwtUser",name = "JwtUser",required = true)
    public ResultInfo createAuthenticationToken(@RequestBody JwtUser authenticationRequest) throws Exception {
        System.out.println("username:"+authenticationRequest.getUsername()+",password:"+authenticationRequest.getPassword());
        userDetailsService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = userDetailsService.createAuthenticationToken(userDetails);
        return ResultInfo.builder().code(200).token(token).data(userDetails).msg("").build();
    }

    @GetMapping("/getAuthenticatedUser")
    @ApiOperation(
            value = "从token中获取用户信息",
            httpMethod = "GET"
    )
    public ResultInfo getAuthenticatedUser(HttpServletRequest request) {
        JwtUser user = userDetailsService.getAuthenticatedUser(request);
        return ResultInfo.builder().msg("获取成功").data(user).code(200).build();
    }

    @GetMapping("/index")
    public ResultInfo index(){
        return ResultInfo.builder().msg("你好").code(200).build();
    }

    @ApiOperation(value = "security登录", notes = "登录验证托管给security")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query"),
    })
    @PostMapping("/doLogin")
    public ResultInfo doLogin(@RequestParam("username") String username, @RequestParam("password")String password, HttpServletRequest req){
        return this.userDetailsService.login(username, password, req);
    }
}
