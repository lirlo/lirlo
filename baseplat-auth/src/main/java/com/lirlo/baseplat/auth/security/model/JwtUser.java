package com.lirlo.baseplat.auth.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.Collection;

@Data
@ApiModel( description = "用户信息")
@TableName("t_sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements Serializable, UserDetails {

    @ApiModelProperty("id")
    public String id;
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static void encrypt() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        for (int i = 0; i < 5; ++i) {
            // 每次生成的密码都不一样
            String encryptedPassword = passwordEncoder.encode("111111");
            System.out.println(encryptedPassword);
            System.out.println(passwordEncoder.matches("111111", encryptedPassword)); // true
//        }
    }

    public static void main(String[] args) {
        encrypt();
    }
}
