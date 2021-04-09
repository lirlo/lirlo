package com.lirlo.baseplat.auth.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel( description = "oauth客户端")
@TableName("oauth_client_details")
public class OauthClientDetails {
    @ApiModelProperty("客户端id")
    private String client_id;
    @ApiModelProperty("指定客户端(client)的访问密匙;")
    private String client_secret;
    @ApiModelProperty("客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: \"unity-resource,mobile-resource\".")
    private String resource_ids;
    @ApiModelProperty("客户端申请的权限范围,可选值包括read,write,trust")
    private String scope;
    @ApiModelProperty("客户端支持的grant_type")
    private String authorized_grant_types;
    @ApiModelProperty("客户端的重定向URI")
    private String web_server_redirect_uri;
    @ApiModelProperty("客户端所拥有的Spring Security的权限值")
    private String authorities;
    @ApiModelProperty("客户端的access_token的有效时间值")
    private int access_token_validity;
    @ApiModelProperty("客户端的refresh_token的有效时间值(单位:秒)")
    private int refresh_token_validity;
    @ApiModelProperty("这是一个预留的字段")
    private String additional_information;
    @ApiModelProperty("用户是否自动Approval操作")
    private String autoapprove;
}
