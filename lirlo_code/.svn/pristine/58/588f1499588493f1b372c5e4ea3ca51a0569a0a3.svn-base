package com.lirlo.system.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel( description = "账号信息")
@TableName("T_SYS_ACCOUNT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class T_SYS_ACCOUNT implements Serializable {
    @ApiModelProperty("账号ID")
    protected String id;

    @ApiModelProperty("姓名")
    protected String name;
    @ApiModelProperty("登录名")
    protected String loginName;
    @ApiModelProperty("密码")
    protected String userPassword;
    @ApiModelProperty("状态 1:正常/2:冻结/3:过期")
    protected BigDecimal status;
    @ApiModelProperty("要求的密码强度")
    protected String passwordStrength;
    @ApiModelProperty("当前密码强度")
    protected String currentPasswordStrength;
    @ApiModelProperty("过期时间, 年月日")
    protected String lifeTime;
    @ApiModelProperty("邮箱,一般用于找回密码")
    protected String email;
    @ApiModelProperty("校内邮箱")
    protected String schoolEmail;
    @ApiModelProperty("昵称")
    protected String nickName;
    @ApiModelProperty("身份证号")
    protected String idCard;
    @ApiModelProperty("手机号")
    protected String mobile;
    @ApiModelProperty("钉钉unionId")
    protected String dingTalkUnionId;
    @ApiModelProperty("钉钉userId")
    protected String dingTalkUserId;
    @ApiModelProperty("")
    protected String qqOpenId;
    @ApiModelProperty("微信公众平台openId")
    protected String wxgzptOpenId;
    protected String appKey;
    @ApiModelProperty(
            value = "权限管理中的主题ID",
            hidden = true
    )
    protected String permissionSubjectId;
    @ApiModelProperty(
            value = "权限管理中的主题所属(PUBLIC/protected)",
            hidden = true
    )
    protected String permissionSubjectBelongType;
    @ApiModelProperty(
            value = "创建人ID",
            hidden = true
    )
    protected String creatorId;
    @ApiModelProperty(
            value = "创建时间",
            hidden = true
    )
    protected String createTime;
    @ApiModelProperty(
            value = "最后更新者ID",
            hidden = true
    )
    protected String updatorId;
    @ApiModelProperty(
            value = "最后更新时间",
            hidden = true
    )
    protected String updateTime;
}
