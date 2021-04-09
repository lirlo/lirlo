package com.lirlo.baseplat.auth.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lirlo.baseplat.auth.security.model.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {
}
