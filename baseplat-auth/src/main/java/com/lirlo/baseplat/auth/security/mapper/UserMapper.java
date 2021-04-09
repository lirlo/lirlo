package com.lirlo.baseplat.auth.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lirlo.baseplat.auth.security.model.JwtUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<JwtUser> {
}
